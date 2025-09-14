/*
 * Copyright 2021 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.firstinspires.ftc.ftccommon.internal;

import android.view.Menu;
import android.content.Context;
import com.qualcomm.ftccommon.FtcEventLoop;
import com.qualcomm.robotcore.util.ClassUtil;
import com.qualcomm.robotcore.util.RobotLog;
import com.qualcomm.robotcore.util.WebHandlerManager;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.firstinspires.ftc.ftccommon.external.OnCreate;
import org.firstinspires.ftc.ftccommon.external.OnCreateEventLoop;
import org.firstinspires.ftc.ftccommon.external.OnCreateMenu;
import org.firstinspires.ftc.ftccommon.external.OnDestroy;
import org.firstinspires.ftc.ftccommon.external.WebHandlerRegistrar;
import org.firstinspires.ftc.robotcore.external.Predicate;
import org.firstinspires.ftc.robotcore.internal.opmode.ClassFilter;
import org.firstinspires.ftc.robotcore.internal.opmode.OnBotJavaDeterminer;

/**
 * Allows methods to be called by the robot controller to add or extend functionality.
 *
 * @author Liz Looney
 */
public class AnnotatedHooksClassFilter implements ClassFilter {
  private static final String TAG = "AnnotatedHooksClassFilter";
  private static final Predicate<Method> isOnBotJava = new Predicate<Method>() {
    @Override public boolean test(Method method) {
      return OnBotJavaDeterminer.isOnBotJava(method.getDeclaringClass());
    }
  };
  private static final Predicate<Method> isExternalLibraries = new Predicate<Method>() {
    @Override public boolean test(Method method) {
      return OnBotJavaDeterminer.isExternalLibraries(method.getDeclaringClass());
    }
  };

  private final Set<Method> onCreateMethods = new HashSet<>();
  private final Set<Method> onCreateEventLoopMethods = new HashSet<>();
  private final Set<Method> onCreateMenuMethods = new HashSet<>();
  private final Set<Method> onDestroyMethods = new HashSet<>();
  private final Set<Method> webHandlerRegistrarMethods = new HashSet<>();
  private final List<Set<Method>> allSets = new ArrayList<>();

  // Singleton

  private static class InstanceHolder {
    public static AnnotatedHooksClassFilter theInstance = new AnnotatedHooksClassFilter();
  }

  public static AnnotatedHooksClassFilter getInstance() {
    return InstanceHolder.theInstance;
  }

  private AnnotatedHooksClassFilter() {
    allSets.add(onCreateMethods);
    allSets.add(onCreateEventLoopMethods);
    allSets.add(onCreateMenuMethods);
    allSets.add(onDestroyMethods);
    allSets.add(webHandlerRegistrarMethods);
  }

  // ClassFilter

  @Override
  public void filterAllClassesStart() {
    clear();
  }

  @Override
  public void filterOnBotJavaClassesStart() {
    removeOldMethods(isOnBotJava);
  }

  @Override
  public void filterExternalLibrariesClassesStart() {
    removeOldMethods(isExternalLibraries);
  }

  @Override
  public void filterClass(Class clazz) {
    exploreClass(clazz);
  }

  @Override
  public void filterOnBotJavaClass(Class clazz) {
    exploreClass(clazz);
  }

  @Override
  public void filterExternalLibrariesClass(Class clazz) {
    exploreClass(clazz);
  }

  @Override
  public void filterAllClassesComplete() {
  }

  @Override
  public void filterOnBotJavaClassesComplete() {
  }

  @Override
  public void filterExternalLibrariesClassesComplete() {
  }

  // public

  public void callOnCreateMethods(Context context) {
    for (Method method : onCreateMethods) {
      try {
        method.invoke(null, context);
      } catch (Exception e) {
        RobotLog.ee(TAG, e, "failure while calling OnCreateMethods annotated method " +
            method.getDeclaringClass() + "." + method.getName());
      }
    }
  }

  public void callOnCreateEventLoopMethods(Context context, FtcEventLoop ftcEventLoop) {
    for (Method method : onCreateEventLoopMethods) {
      try {
        method.invoke(null, context, ftcEventLoop);
      } catch (Exception e) {
        RobotLog.ee(TAG, e, "failure while calling onCreateEventLoopMethods annotated method " +
            method.getDeclaringClass() + "." + method.getName());
      }
    }
  }

  public void callOnCreateMenuMethods(Context context, Menu menu) {
    for (Method method : onCreateMenuMethods) {
      try {
        method.invoke(null, context, menu);
      } catch (Exception e) {
        RobotLog.ee(TAG, e, "failure while calling onCreateMenuMethods annotated method " +
            method.getDeclaringClass() + "." + method.getName());
      }
    }
  }

  public void callOnDestroyMethods(Context context) {
    for (Method method : onDestroyMethods) {
      try {
        method.invoke(null, context);
      } catch (Exception e) {
        RobotLog.ee(TAG, e, "failure while calling onDestroyMethods annotated method " +
            method.getDeclaringClass() + "." + method.getName());
      }
    }
  }

  public void callWebHandlerRegistrarMethods(Context context, WebHandlerManager webHandlerManager) {
    for (Method method : webHandlerRegistrarMethods) {
      try {
        method.invoke(null, context, webHandlerManager);
      } catch (Exception e) {
        RobotLog.ee(TAG, e, "failure while calling WebHandlerRegistrarMethods annotated method " +
            method.getDeclaringClass() + "." + method.getName());
      }
    }
  }

  // private

  private void clear() {
    for (Set<Method> set : allSets) {
      set.clear();
    }
  }

  private void removeOldMethods(Predicate<Method> predicate) {
    for (Set<Method> set : allSets) {
      Iterator<Method> iter = set.iterator();
      while (iter.hasNext()) {
        Method method = iter.next();
        if (predicate.test(method)) {
          iter.remove();
        }
      }
    }
  }

  private void exploreClass(Class<?> clazz) {
    for (Method method : ClassUtil.getLocalDeclaredMethods(clazz)) {
      int modifiers = method.getModifiers();
      int requiredModifiers = Modifier.PUBLIC | Modifier.STATIC;
      if ((modifiers & requiredModifiers) != requiredModifiers) {
        continue;
      }
      int prohibitedModifiers = Modifier.ABSTRACT;
      if ((method.getModifiers() & prohibitedModifiers) != 0) {
        continue;
      }

      if (method.isAnnotationPresent(OnCreate.class) &&
          method.getParameterTypes().length == 1) {
        onCreateMethods.add(method);
      }
      if (method.isAnnotationPresent(OnCreateEventLoop.class) &&
          method.getParameterTypes().length == 2) {
        onCreateEventLoopMethods.add(method);
      }
      if (method.isAnnotationPresent(OnCreateMenu.class) &&
          method.getParameterTypes().length == 2) {
        onCreateMenuMethods.add(method);
      }
      if (method.isAnnotationPresent(OnDestroy.class) &&
          method.getParameterTypes().length == 1) {
        onDestroyMethods.add(method);
      }
      if (method.isAnnotationPresent(WebHandlerRegistrar.class) &&
          method.getParameterTypes().length == 2) {
        webHandlerRegistrarMethods.add(method);
      }
    }
  }
}
