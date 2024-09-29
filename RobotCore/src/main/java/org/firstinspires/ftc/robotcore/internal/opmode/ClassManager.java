/*
 * Copyright (c) 2016 Craig MacFarlane
 *   Based upon work by David Sargent and Bob Atkinson
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted
 * (subject to the limitations in the disclaimer below) provided that the following conditions are
 * met:
 *
 * Redistributions of source code must retain the above copyright notice, this list of conditions
 * and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions
 * and the following disclaimer in the documentation and/or other materials provided with the
 * distribution.
 *
 * Neither the name of Craig MacFarlane nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS LICENSE. THIS
 * SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF
 * THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.robotcore.internal.opmode;

import android.content.Context;

import com.qualcomm.robotcore.util.RobotLog;
import com.qualcomm.robotcore.util.Util;

import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.firstinspires.ftc.robotcore.internal.system.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import dalvik.system.DexFile;

/**
 * Finds all of the classes in the APK and provides an extensible mechanism
 * for iterating and selecting classes for particular needs.  Classes that want
 * to select particular classes should implement ClassFilter and register
 * themselves here.  See ClassManagerFactory.
 *
 * This is predicated on the notion that the set of classes in any given APK is
 * constant.  So a class may implement ClassFilter, populate a static list of
 * classes it is interested in, and then use that static list in any instance of
 * the class knowing that the list is not dynamic over any given install of the APK.
 * See AnnotatedOpModeRegistrar.
 */
@SuppressWarnings("WeakerAccess")
public class ClassManager {

    //----------------------------------------------------------------------------------------------
    // State
    //----------------------------------------------------------------------------------------------

    private static class InstanceHolder
    {
        public static ClassManager theInstance = new ClassManager();
    }
    public static ClassManager getInstance()
    {
        return InstanceHolder.theInstance;
    }

    private static final String TAG = "ClassManager";
    private static final boolean DEBUG = false;

    private static final AtomicBoolean processAllClassesCalled = new AtomicBoolean(false);

    private List<String> packagesAndClassesToIgnore;
    private Set<ClassFilter> filters; // Must be a set so that adds are idempotent.
    private Context context;
    private DexFile dexFile;
    private OnBotJavaHelper onBotJavaHelper = null;
    private ClassLoader classLoader = null;

    //----------------------------------------------------------------------------------------------
    // Construction
    //----------------------------------------------------------------------------------------------

    private ClassManager()
    {
        try
        {
            this.context = AppUtil.getInstance().getApplication();
            this.dexFile = new DexFile(this.context.getPackageCodePath());
            this.filters = new HashSet<>();
            resetIgnoredList();
        }
        catch (Exception e)
        {
            throw AppUtil.getInstance().unreachable(TAG, e);
        }

    }

    protected void resetIgnoredList()
    {
        // We ignore certain packages to make us more robust and efficient.
        // Classes in these packages will not be put through our various ClassFilter implementations.
        // This means that they will not be scanned for any annotations that are defined in the
        // FTC SDK, such as @TeleOp, or @DeviceProperties, or for configuration XML files.
        this.packagesAndClassesToIgnore = new ArrayList<String>();
        this.packagesAndClassesToIgnore.addAll(Arrays.asList(
            "android", // Also excludes androidx
            "com.android",
            "com.google",
            "com.qualcomm.robotcore.wifi",
            "com.sun",
            "gnu.kawa.swingviews",
            "io.netty",
            "java", // Also excludes javax
            "kawa",
            "org.apache",
            "org.checkerframework",
            "org.firstinspires.ftc.robotcore.internal.android",
            "org.java_websocket",
            "org.slf4j",
            "org.threeten",
            "com.journeyapps"
        ));
    }

    protected void clearOnBotJava()
    {
    }

    public void setOnBotJavaClassHelper(OnBotJavaHelper helper)
    {
        this.onBotJavaHelper = helper;
    }

    private void setClassLoader(ClassLoader classLoader)
    {
        this.classLoader = classLoader;
    }

    //----------------------------------------------------------------------------------------------
    // Operations
    //----------------------------------------------------------------------------------------------

    /**
     * You want to know what classes are in the APK?  Call me.
     * <p>
     * This method is idempotent.
     *
     * @param filter a class that implements ClassFilter.
     */
    public void registerFilter(ClassFilter filter)
    {
        if (processAllClassesCalled.get())
        {
            RobotLog.addGlobalWarningMessage("Class processing had already started when registerFilter() was called. " +
                    "You must register your class filter before processAllClasses() is called in FtcRobotControllerActivity.");
            return;
        }
        filters.add(filter);
    }

    /**
     * Find all the classes in the context in which we should consider looking, which
     * (currently?) is the entire .APK in which we are found plus any on bot java classes.
     * Classes can be found in either the base apk dex file, or any number of
     * instant run dex files, or in On-Bot Java classes. Gather them all...
     */
    private List<String> getAllClassNames()
    {
        // Load what's built into the APK
        List<String> classNames = new ArrayList<String>(Collections.list(dexFile.entries()));

        // Deal with instant run's craziness
        classNames.addAll(InstantRunHelper.getAllClassNames(context));

        if (onBotJavaHelper != null) {
            // Load classes from OnBotJava
            classNames.addAll(onBotJavaHelper.getOnBotJavaClassNames());
            setClassLoader(onBotJavaHelper.createOnBotJavaClassLoader());

            // Load classes from external libraries.
            classNames.addAll(onBotJavaHelper.getExternalLibrariesClassNames());
        }

        return classNames;
    }

    protected List<Class> classNamesToClasses(Collection<String> classNames)
    {
        List<Class> result = new LinkedList<Class>();
        ClassLoader classLoaderToUse = (classLoader != null)
            ? classLoader
            : this.getClass().getClassLoader();
        for (String className : classNames)
        {
            // Ignore classes that are in some packages that we know aren't worth considering
            boolean shouldIgnore = false;
            for (String packageName : packagesAndClassesToIgnore)
            {
                if (Util.isPrefixOf(packageName, className))
                {
                    shouldIgnore = true;
                    break;
                }
            }
            if (shouldIgnore)
                continue;

            // Get the Class from the className
            Class clazz;
            try
            {
                clazz = Class.forName(className, false, classLoaderToUse);
                if (DEBUG) RobotLog.ii(TAG, "class %s: loader=%s", className, clazz.getClassLoader().getClass().getSimpleName());
            }
            catch (NoClassDefFoundError|ClassNotFoundException ex)
            {
                // We can't find that class
                if (logClassNotFound(className)) RobotLog.ww(TAG, ex, className + " " + ex.toString());
                if (className.contains("$"))
                {
                    // Prevent loading similar inner classes, a performance optimization
                    className = className.substring(0, className.indexOf("$") /* -1 */);
                }

                packagesAndClassesToIgnore.add(className);
                continue;
            }

            // Remember that class
            result.add(clazz);
        }

        return result;
    }

    protected boolean logClassNotFound(String className)
    {
        // TODO: Uncomment this code and add a string to the prefixes array if you need to ignore a class not being found
//        String[] prefixes = { };
//        for (String prefix : prefixes)
//        {
//            if (className.startsWith(prefix))
//            {
//                return false;
//            }
//        }
        return true;
    }

    /**
     * Iterate over all the classes in the APK and other sources and call registered filters.
     */
    public void processAllClasses()
    {
        if (processAllClassesCalled.getAndSet(true))
        {
            RobotLog.addGlobalWarningMessage("processAllClasses() should only be called by the system. " +
                    "Any additional calls are ignored.");
            return;
        }

        resetIgnoredList();
        List<Class> allClasses = classNamesToClasses(getAllClassNames());

        for (ClassFilter f : filters)
        {
            f.filterAllClassesStart();
            for (Class clazz : allClasses)
            {
                f.filterClass(clazz);
            }
            f.filterAllClassesComplete();
        }
    }

    public void processOnBotJavaClasses()
    {
        if (onBotJavaHelper == null) {
            return;
        }

        resetIgnoredList();
        Collection<String> classNames = onBotJavaHelper.getOnBotJavaClassNames();
        setClassLoader(onBotJavaHelper.createOnBotJavaClassLoader());
        List<Class> onBotJavaClasses = classNamesToClasses(classNames);

        for (ClassFilter f : filters)
        {
            f.filterOnBotJavaClassesStart();
            for (Class clazz : onBotJavaClasses)
            {
                Assert.assertTrue(OnBotJavaDeterminer.isOnBotJava(clazz),
                    "class %s isn't OnBotJava: loader=%s", clazz.getSimpleName(), clazz.getClassLoader().getClass().getSimpleName());
                f.filterOnBotJavaClass(clazz);
            }
            f.filterOnBotJavaClassesComplete();
        }
    }

    /**
     * Processes the classes from external libraries. Called after a new external library (a .jar
     * or a .aar) file is uploaded.
     */
    public void processExternalLibrariesClasses()
    {
        if (onBotJavaHelper == null) {
            return;
        }

        resetIgnoredList();
        Collection<String> classNames = onBotJavaHelper.getExternalLibrariesClassNames();
        // The OnBotJavaClassLoader sits on top of the ExternalLibraries ClassLoader, so we need to
        // get a new OnBotJavaClassLoader.
        setClassLoader(onBotJavaHelper.createOnBotJavaClassLoader());
        List<Class> externalLibrariesClasses = classNamesToClasses(classNames);

        for (ClassFilter f : filters)
        {
            f.filterExternalLibrariesClassesStart();
            for (Class clazz : externalLibrariesClasses)
            {
                Assert.assertTrue(OnBotJavaDeterminer.isExternalLibraries(clazz),
                    "class %s isn't ExternalLibraries: loader=%s", clazz.getSimpleName(), clazz.getClassLoader().getClass().getSimpleName());
                f.filterExternalLibrariesClass(clazz);
            }
            f.filterExternalLibrariesClassesComplete();
        }
    }
}
