/*
Copyright (c) 2017 Robert Atkinson

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Robert Atkinson nor the names of his contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package org.firstinspires.ftc.robotcore.internal.opmode;

import androidx.annotation.Nullable;

import com.qualcomm.robotcore.R;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManager;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManagerImpl;
import com.qualcomm.robotcore.eventloop.opmode.OpModeRegister;
import com.qualcomm.robotcore.exception.DuplicateNameException;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.Supplier;
import org.firstinspires.ftc.robotcore.internal.files.FileModifyObserver;
import org.firstinspires.ftc.robotcore.internal.files.RecursiveFileObserver;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.firstinspires.ftc.robotcore.internal.system.Assert;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * {@link RegisteredOpModes} is the owner of a set of currently-registered OpModes.
 */
@SuppressWarnings("WeakerAccess")
public class RegisteredOpModes implements OpModeManager
    {
    //----------------------------------------------------------------------------------------------
    // State
    //----------------------------------------------------------------------------------------------

    public static final String TAG = "RegisteredOpModes";
    public static OpModeMeta DEFAULT_OP_MODE_METADATA = new OpModeMeta.Builder()
            .setName(DEFAULT_OP_MODE_NAME)
            .setFlavor(OpModeMeta.Flavor.SYSTEM)
            .setSystemOpModeBaseDisplayName(AppUtil.getDefContext().getString(R.string.defaultOpModeName))
            .build();


    protected static class InstanceHolder
        {
        public static final RegisteredOpModes theInstance = new RegisteredOpModes();
        }
    public static RegisteredOpModes getInstance() { return InstanceHolder.theInstance; }

    protected final Object opModesLock = new Object();
    protected boolean opModesAreLocked = false;
    protected Map<String, OpModeMetaAndClass> opModeClasses = new LinkedHashMap<String, OpModeMetaAndClass>();
    protected Map<String, OpModeMetaAndInstance> opModeInstances = new LinkedHashMap<String, OpModeMetaAndInstance>();
    protected volatile boolean opmodesAreRegistered = false;

    protected final List<InstanceOpModeRegistrar> instanceOpModeRegistrars = new ArrayList<InstanceOpModeRegistrar>();

    protected RecursiveFileObserver blocksOpModeMonitor;
    protected volatile boolean      blocksOpModesChanged;
    protected FileModifyObserver    onBotJavaMonitor;
    protected volatile boolean      onBotJavaChanged;
    protected volatile boolean      externalLibrariesChanged;

    //----------------------------------------------------------------------------------------------
    // Construction
    //----------------------------------------------------------------------------------------------

    public RegisteredOpModes()
        {
        // Setup OnBotJava monitoring system
        onBotJavaChanged = false;
        onBotJavaMonitor = new FileModifyObserver(OnBotJavaHelper.buildSuccessfulFile, new FileModifyObserver.Listener()
            {
            @Override
            public void onFileChanged(int event, File file)
                {
                RobotLog.vv(TAG, "noting that OnBotJava changed");
                onBotJavaChanged = true;
                }
            });

        // We don't need a monitoring system for external libraries because
        // setExternalLibrariesChanged is called when an external library
        // is uploaded or deleted.
        externalLibrariesChanged = false;

        // Setup Blocks monitoring system
        blocksOpModesChanged = false;
        final int blocksOpModeMonitorAccess = RecursiveFileObserver.CLOSE_WRITE | RecursiveFileObserver.DELETE | RecursiveFileObserver.MOVED_FROM | RecursiveFileObserver.MOVED_TO;
        blocksOpModeMonitor = new RecursiveFileObserver(AppUtil.BLOCK_OPMODES_DIR, blocksOpModeMonitorAccess, RecursiveFileObserver.Mode.NONRECURSVIVE, new RecursiveFileObserver.Listener()
            {
            @Override
            public void onEvent(int event, File file)
                {
                if ((event & blocksOpModeMonitorAccess) != 0)
                    {
                    if (file.getName().endsWith(AppUtil.BLOCKS_JS_EXT) || file.getName().endsWith(AppUtil.BLOCKS_BLK_EXT))
                        {
                        RobotLog.vv(TAG, "noting that Blocks changed");
                        blocksOpModesChanged = true;
                        }
                    }
                }
            });
        blocksOpModeMonitor.startWatching();
        }

    //----------------------------------------------------------------------------------------------
    // Change management
    //----------------------------------------------------------------------------------------------

    public boolean getOnBotJavaChanged()
        {
        return onBotJavaChanged;
        }
    public void clearOnBotJavaChanged()
        {
        onBotJavaChanged = false;
        }

    public boolean getExternalLibrariesChanged()
        {
        return externalLibrariesChanged;
        }
    public void setExternalLibrariesChanged()
        {
        externalLibrariesChanged = true;
        }
    public void clearExternalLibrariesChanged()
        {
        externalLibrariesChanged = false;
        }

    public boolean getBlocksOpModesChanged()
        {
        return blocksOpModesChanged;
        }
    public void clearBlocksOpModesChanged()
        {
        blocksOpModesChanged = false;
        }

    //----------------------------------------------------------------------------------------------
    // OpMode management
    //----------------------------------------------------------------------------------------------

    public void addInstanceOpModeRegistrar(InstanceOpModeRegistrar register)
        {
        synchronized (instanceOpModeRegistrars)
            {
            instanceOpModeRegistrars.add(register);
            }
        }

    public void registerAllOpModes(final OpModeRegister userOpmodeRegister)
        {
        lockOpModesWhile(new Runnable()
            {
            @Override
            public void run()
                {
                opModeClasses.clear();
                opModeInstances.clear();

                // register our default OpMode first, that way the user can override it (eh?)
                register(DEFAULT_OP_MODE_METADATA, OpModeManagerImpl.DefaultOpMode.class);

                // Somewhat arbitrary, but do the annotated ones LAST so we
                // can get the same 'duplicate name' behavior on reregistration
                // as we do on original registration
                callInstanceOpModeRegistrars();
                userOpmodeRegister.register(RegisteredOpModes.this);
                AnnotatedOpModeClassFilter.getInstance().registerAllClasses(RegisteredOpModes.this);

                opmodesAreRegistered = true;
                }
            });
        }

    protected void callInstanceOpModeRegistrars()
        {
        synchronized (instanceOpModeRegistrars)
            {
            for (final InstanceOpModeRegistrar instanceOpModeRegistrar : instanceOpModeRegistrars)
                {
                instanceOpModeRegistrar.register(new InstanceOpModeManager()
                    {
                    @Override
                    public void register(OpModeMeta meta, OpMode opModeInstance)
                        {
                        RegisteredOpModes.this.register(meta, opModeInstance, instanceOpModeRegistrar);
                        }
                    });
                }
            }
        }

    public void registerOnBotJavaOpModes()
        {
        lockOpModesWhile(new Runnable()
            {
            @Override
            public void run()
                {

                // Unregister any existing OpModes that were dynamically loaded
                List<OpModeMetaAndClass> extant = new ArrayList<>(opModeClasses.values());
                for (OpModeMetaAndClass opModeMetaAndClass : extant)
                    {
                    if (opModeMetaAndClass.isOnBotJava())
                        {
                        Assert.assertTrue(opModeClasses.get(opModeMetaAndClass.meta.name) == opModeMetaAndClass);
                        unregister(opModeMetaAndClass.meta);
                        }
                    }

                // Add any new OpModes
                ClassManager.getInstance().processOnBotJavaClasses();
                AnnotatedOpModeClassFilter.getInstance().registerOnBotJavaClasses(RegisteredOpModes.this);
                }
            });
        }

    public void registerExternalLibrariesOpModes()
        {
        lockOpModesWhile(new Runnable()
            {
            @Override
            public void run()
                {

                // Unregister any existing OpModes that were dynamically loaded
                List<OpModeMetaAndClass> extant = new ArrayList<>(opModeClasses.values());
                for (OpModeMetaAndClass opModeMetaAndClass : extant)
                    {
                    if (opModeMetaAndClass.isExternalLibraries())
                        {
                        Assert.assertTrue(opModeClasses.get(opModeMetaAndClass.meta.name) == opModeMetaAndClass);
                        unregister(opModeMetaAndClass.meta);
                        }
                    }

                // Add any new OpModes
                AnnotatedOpModeClassFilter.getInstance().registerExternalLibrariesClasses(RegisteredOpModes.this);
                }
            });
        }

    public void registerInstanceOpModes()
        {
        lockOpModesWhile(new Runnable()
            {
            @Override
            public void run()
                {

                // Unregister existing instance OpModes
                synchronized (instanceOpModeRegistrars)
                    {
                    List<OpModeMetaAndInstance> extant = new ArrayList<OpModeMetaAndInstance>(opModeInstances.values());
                    for (InstanceOpModeRegistrar instanceOpModeRegistrar : instanceOpModeRegistrars)
                        {
                        for (OpModeMetaAndInstance opModeMetaAndInstance : extant)
                            {
                            if (opModeMetaAndInstance.instanceOpModeRegistrar == instanceOpModeRegistrar)
                                {
                                unregister(opModeMetaAndInstance.meta);
                                }
                            }
                        }
                    }

                // Register any new ones
                callInstanceOpModeRegistrars();
                }
            });
        }

    public void waitOpModesRegistered()
        {
        while (!opmodesAreRegistered)
            {
            Thread.yield();
            }
        }

    public void lockOpModesWhile(Runnable runnable)
        {
        synchronized (opModesLock)
            {
            opModesAreLocked = true;
            try
                {
                runnable.run();
                }
            finally
                {
                opModesAreLocked = false;
                }
            }
        }

    public <T> T lockOpModesWhile(Supplier<T> supplier)
        {
        synchronized (opModesLock)
            {
            opModesAreLocked = true;
            try
                {
                return supplier.get();
                }
            finally
                {
                opModesAreLocked = false;
                }
            }
        }

    public List<OpModeMeta> getOpModes()
        {
        return lockOpModesWhile(new Supplier<List<OpModeMeta>>()
            {
            @Override
            public List<OpModeMeta> get()
                {
                Assert.assertTrue(opmodesAreRegistered);
                List<OpModeMeta> result = new LinkedList<OpModeMeta>();
                for (OpModeMetaAndClass opModeMetaAndClassData : opModeClasses.values())
                    {
                    result.add(opModeMetaAndClassData.meta);
                    }
                for (OpModeMetaAndInstance opModeMetaAndInstance : opModeInstances.values())
                    {
                    result.add(opModeMetaAndInstance.meta);
                    }
                return result;
                }
            });
        }

    public OpMode getOpMode(final String opModeName)
        {
        return lockOpModesWhile(new Supplier<OpMode>()
            {
            @Override public OpMode get()
                {
                try
                    {
                    if (opModeInstances.containsKey(opModeName))
                        {
                        return opModeInstances.get(opModeName).instance;
                        }
                    else if (opModeClasses.containsKey(opModeName))
                        {
                        return opModeClasses.get(opModeName).clazz.newInstance();
                        }
                    else
                        {
                        return null;
                        }
                    }
                catch (InstantiationException|IllegalAccessException e)
                    {
                    return null;
                    }
                }
            });
        }

    @Nullable public OpModeMeta getOpModeMetadata(final String opModeName)
        {
        return lockOpModesWhile(() ->
            {
            if (opModeInstances.containsKey(opModeName))
                {
                return opModeInstances.get(opModeName).meta;
                }
            else if (opModeClasses.containsKey(opModeName))
                {
                return opModeClasses.get(opModeName).meta;
                }
            else
                {
                return null;
                }
            });
        }

    protected boolean reportIfOpModeAlreadyRegistered(OpModeMeta meta)
        {
        if (isOpmodeRegistered(meta))
            {
            String message = String.format(AppUtil.getDefContext().getString(R.string.warningDuplicateOpMode), meta.name);
            // Show the message in the log
            RobotLog.ww(TAG, "configuration error: %s", message);
            // Make the message appear on the driver station
            RobotLog.addGlobalWarningMessage(message);
            return false;
            }
        return true;
        }

    protected boolean isOpmodeRegistered(OpModeMeta meta)
        {
        Assert.assertTrue(opModesAreLocked);
        return opModeClasses.containsKey(meta.name) || opModeInstances.containsKey(meta.name);
        }

        /**
         * This renames an OpMode by unregistering it with the old name and then
         * registering it with the new name.   If the old name didn't exist, it
         * does nothing.
         * @param oldName - the name as it is registered
         */
    public void renameOpModeWithClass(String oldName)
        {
        lockOpModesWhile(new Runnable()
        {
            @Override
            public void run()
                {
                if (opModeClasses.containsKey(oldName))
                    {
                    OpModeMetaAndClass opModeMetaAndClass = opModeClasses.get(oldName);

                    unregister(opModeMetaAndClass.meta);
                    String newName = oldName + '-' + opModeMetaAndClass.clazz.getSimpleName();
                    register(new OpModeMeta.Builder().setName(newName).build(),
                            opModeMetaAndClass.clazz);
                    }
                else if (opModeInstances.containsKey(oldName))
                    {
                    OpModeMetaAndInstance opModeMetaAndInstance = opModeInstances.get(oldName);

                    unregister(opModeMetaAndInstance.meta);
                    String newName = oldName + '-' + opModeMetaAndInstance.getClass().getSimpleName();

                    register(new OpModeMeta.Builder().setName(newName).build(),
                            opModeMetaAndInstance.instance, null);
                    }
                }
            });
        }
    //----------------------------------------------------------------------------------------------
    // Registration and unregistration
    //----------------------------------------------------------------------------------------------

    /**
     * Registers an OpMode <em>class</em> with the name by which it should be known in the driver station.
     *
     * @param name   the name of the OpMode in the driver station
     * @param opMode the OpMode class to instantiate when that OpMode is selected
     */
    public void register(String name, Class opMode)
        {
        register(new OpModeMeta.Builder().setName(name).build(), opMode);
        }

    public void register(final OpModeMeta meta, final Class opMode)
        {
        lockOpModesWhile(new Runnable()
            {
            @Override
            public void run()
                {
                if (reportIfOpModeAlreadyRegistered(meta))
                    {
                    opModeClasses.put(meta.name, new OpModeMetaAndClass(meta, (Class<OpMode>) opMode));
                    RobotLog.vv(AnnotatedOpModeClassFilter.TAG, String.format("registered {%s} as {%s}", opMode.getSimpleName(), meta.name));
                    }
                else
                    {
                    throw new DuplicateNameException("Duplicate for " + meta.name);
                    }
                }
            });
        }

    /**
     * Registers an OpMode <em>instance</em> with the name by which it should be known in the driver station.
     * This should only be used in environments where it is not possible to pass Class objects. In particular, it
     * should not be used with OpModes authored (in Java) using Android Studio.
     *
     * @param name   the name of the OpMode in the driver station
     * @param opMode the OpMode instance to use when that OpMode is selected
     */
    public void register(String name, OpMode opMode)
        {
        register(new OpModeMeta.Builder().setName(name).build(), opMode);
        }

    public void register(final OpModeMeta meta, final OpMode opModeInstance)
        {
        register(meta, opModeInstance, null);
        }

    public void register(final OpModeMeta meta, final OpMode opModeInstance, @Nullable final InstanceOpModeRegistrar instanceOpModeRegistrar)
        {
        lockOpModesWhile(new Runnable()
            {
            @Override
            public void run()
                {
                if (reportIfOpModeAlreadyRegistered(meta))
                    {
                    opModeInstances.put(meta.name, new OpModeMetaAndInstance(meta, opModeInstance, instanceOpModeRegistrar));
                    RobotLog.vv(AnnotatedOpModeClassFilter.TAG, String.format("registered instance as {%s}", meta));
                    }
                }
            });
        }

    protected void unregister(final OpModeMeta meta)
        {
        lockOpModesWhile(new Runnable()
            {
            @Override
            public void run()
                {
                RobotLog.vv(AnnotatedOpModeClassFilter.TAG, "unregistered {%s}", meta.name);
                opModeClasses.remove(meta.name);
                opModeInstances.remove(meta.name);
                Assert.assertFalse(isOpmodeRegistered(meta));
                }
            });
        }

    }
