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
package org.firstinspires.ftc.onbotjava;

import android.content.res.AssetManager;
import android.os.Build;

import androidx.annotation.Nullable;

import com.android.tools.r8.CompilationFailedException;
import com.android.tools.r8.D8;
import com.android.tools.r8.D8Command;

import com.qualcomm.robotcore.util.ReadWriteFile;
import com.qualcomm.robotcore.util.RobotLog;
import com.qualcomm.robotcore.util.ThreadPool;

import dk.sgjesse.d8onandroid.D8DiagnosticsHandler;
import dk.sgjesse.r8api.ArchiveClassFileResourceProvider;
import dk.sgjesse.r8api.ArchiveProgramResourceProvider;
import dk.sgjesse.r8api.AndroidDexIndexedConsumer;
import dk.sgjesse.r8api.OrderedClassFileResourceProvider;

import org.firstinspires.ftc.robotcore.external.Supplier;
import org.firstinspires.ftc.robotcore.external.ThrowingCallable;
import org.firstinspires.ftc.robotcore.internal.files.FileModifyObserver;
import org.firstinspires.ftc.robotcore.internal.opmode.OnBotJavaBuildLocker;
import org.firstinspires.ftc.robotcore.internal.opmode.OnBotJavaHelper;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.firstinspires.ftc.robotcore.internal.system.Assert;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneOffset;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * {@link OnBotJavaManager} is the main orchestrator of the OnBotJava build process.
 * <p>
 * The process is driven by reading and writing of files. In the FIRST directory, we now have
 * a 'java' child. This class owns and manages that space. There are several subdirectories
 * of 'java':
 *  <ol>
 *      <li>src:  .java source code is placed here in package-appropriate subdirectories, in the
 *                 usual Java style</li>
 *      <li>jars: (optional) Any externally-compiled jar files can be placed in this
 *                directory. They will be installed in the system, much as the .java source
 *                files are after they have been compiled.</li>
 *      <li>lib:  Library files that source code is to be compiled against are placed here.
 *                Normally, this is automatically-managed: the system copies the necessary
 *                Android and FTC SDK libraries.</li>
 *      <li>control: Whenever the contents of the 'buildRequest.txt' file in this directory is
 *                changed, a build is automatically kicked off it's not already running. Note
 *                that the contents of the file don't matter, only the act of changing it. This
 *                directory also contains a locking mechanism so as to allow clients to examine
 *                the state of a successful build without having to worry that a new build will
 *                be kicked off while they are doing so. See 'buildLock' below.</li>
 *      <li>build: When a build runs, the binary artifacts for intermediate and final output
 *                state are managed in subdirectories thereof.</li>
 *      <li>status: As the build progresses, several files are written. These files may be
 *                monitored for changes (see {@link FileModifyObserver}) as triggers to take
 *                actions to process the output of the build.
 *                <ol>
 *                      <li>buildStarted.txt:    updated when the build starts</li>
 *                      <li>buildSuccessful.txt: updated when the build has been determined
 *                                               to be successful</li>
 *                      <li>buildComplete.txt:   updated when the build finishes, whether
 *                                               or not it was successful</li>
 *                      <li>buildLog.txt:        contains unstructured output from the compiler
 *                                               and other build tools. Note that after successful
 *                                               builds, this will likely be empty.</li>
 *                </ol>
 *                To this list it might be reasonable to add buildLog.xml that had a structured
 *                version of buildLog.txt, but that has not yet been implemented. Feedback is welcome.
 *                </li>
 *  </ol>
 *  <p>
 *  The Annotated OpMode loading system is a client of this structure. It monitors for successful
 *  builds, and upon detection of same, (re)scans the output of the build and alters its sense of
 *  available OpModes accordingly.
 */
@SuppressWarnings("WeakerAccess")
public class OnBotJavaManager implements Closeable
    {
    //----------------------------------------------------------------------------------------------
    // Static State
    //
    // Style note: files & dirs are camelCase
    //----------------------------------------------------------------------------------------------

    public static final File javaRoot               = new File(AppUtil.FIRST_FOLDER, "/java/");

    /** the directory into which libraries used by the source live */
    public static final File libDir                 = new File(javaRoot, "/lib/");

    /** the directory into which user .java should be placed (in
     * appropriate reverse-domain subdirs for .java, as usual) */
    public static final File srcDir                 = new File(javaRoot, "/src/");

    /** The directory into which source code is backed up on builds **/
    public static final File srcBackupDir           = new File(javaRoot, "/srcBackups");

    // Support for external libraries (uploaded .jar and .aar files)
    public static final String EXTERNAL_LIBRARIES = "/ExternalLibraries";
    public static final File extLibDir            = new File(AppUtil.FIRST_FOLDER, EXTERNAL_LIBRARIES);

    public static final File controlDir             = new File(javaRoot, "/control/");
    public static final File buildRequestFile       = new File(controlDir, "buildRequest.txt");
    public static final File buildLockDir           = new File(controlDir, "/buildLock/");

    public static final File statusDir              = new File(javaRoot, "/status/");
    public static final File buildLogFile           = new File(statusDir, "buildLog.txt");
    public static final File buildStartedFile       = new File(statusDir, "buildStarted.txt");
    public static final File buildCompleteFile      = new File(statusDir, "buildComplete.txt");
    public static final File currentOnBotJavaDirFile = new File(statusDir, "currentOnBotJavaDir.txt");

    /** the root of all outputs generated by the build system. */
    public static final File buildDir               = new File(javaRoot, "/build/");
    public static final File sourceOutputDir        = new File(buildDir, "/gensrc/");
    public static final File classesOutputDir       = new File(buildDir, "/classes/");
    public static final File jarsOutputDir          = new File(buildDir, "/jars/");
    public static final String onBotJavaJarName     = "OnBotJava.jar";

    public static final File assetRoot              = new File("java");
    public static final String[] platformClassPathLibs = new String[] { "android.jar", "androidx-rs.jar" };
    private static final String[] ftcClassPathLibs      = new String[] { "onbotjava-classes.jar", "desugar_jdk_libs-1.1.1.jar", "core-lambda-stubs-30.0.3.jar" };

    //----------------------------------------------------------------------------------------------
    // State
    //----------------------------------------------------------------------------------------------

    public static final String TAG = "OnBotJava";

    protected static final Object                      startStopLock = new Object();
    protected final ThreadPool.Singleton<Void>  buildSingleton;
    protected FileModifyObserver                controlObserver;
    protected OnBotJavaDiagnosticsListener      diagnosticListener;
    protected volatile static BuildStatus buildStatus = BuildStatus.NOT_STARTED;

    public enum BuildStatus
        {
        RUNNING,
        FAILED,
        SUCCESSFUL,
        NOT_STARTED
        }

    //----------------------------------------------------------------------------------------------
    // Initialization
    //----------------------------------------------------------------------------------------------

    static {
        RobotLog.vv(TAG, "OnBotJavaManager::static");
        initialize();
        }

    public static void initialize()
        {
        ensureDirectories();
        extractAssets();
        }

    protected static void ensureDirectories()
        {
        ensureDirs(libDir);
        ensureDirs(srcDir);
        ensureDirs(srcBackupDir);
        ensureDirs(extLibDir);
        ensureDirs(controlDir);
        ensureDirs(statusDir);
        ensureDirs(buildDir);
        ReadWriteFile.ensureAllChangesAreCommitted(srcDir);
        }

    protected void ensureBuildDirs()
        {
        trace("ensureBuildDirs()", new Runnable()
            {
            @Override public void run()
                {
                ensureDirs(sourceOutputDir);
                ensureDirs(classesOutputDir);
                ensureDirs(jarsOutputDir);
                }
            });
        }

    protected static void ensureDirs(File file)
        {
        AppUtil.getInstance().ensureDirectoryExists(file);
        }

    protected static void extractAssets()
        {
        for (String libName : OnBotJavaManager.platformClassPathLibs)
            {
            extractJavaLibraryAsset(libName);
            }
        for (String libName : OnBotJavaManager.ftcClassPathLibs)
            {
            extractJavaLibraryAsset(libName);
            }
        }

    protected static void extractJavaLibraryAsset(String name)
        {
        File asset = new File(assetRoot, name);
        File libFile = new File(libDir, name);

        try
            {
            AssetManager assetManager = AppUtil.getDefContext().getAssets();
            try (InputStream inputStream = assetManager.open(asset.getPath(), AssetManager.ACCESS_STREAMING))
                {
                if (!libFile.exists() || asset.length() != libFile.length() || asset.lastModified() != libFile.lastModified())
                    {
                    AppUtil.getInstance().copyStream(inputStream, libFile);
                    libFile.setLastModified(asset.lastModified());
                    }
                }
            }
        catch (IOException e)
            {
            throw new RuntimeException("exception in extractJavaLibraryAsset()", e);
            }
        }

    //----------------------------------------------------------------------------------------------
    // Construction
    //----------------------------------------------------------------------------------------------

    public OnBotJavaManager()
        {
        RobotLog.vv(TAG, "ctor()...");
        buildSingleton = new ThreadPool.Singleton<Void>();
        buildSingleton.setService(ThreadPool.getDefault());

        controlObserver = new FileModifyObserver(buildRequestFile, new FileModifyObserver.Listener()
            {
            @Override public void onFileChanged(int event, File file)
                {
                RobotLog.vv(TAG, "build request file changed: 0x%08x %s", event, file.getAbsolutePath());
                buildSingleton.submit(new Runnable()
                    {
                    @Override public void run()
                        {
                        OnBotJavaBuildLocker.lockBuildExclusiveWhile(new Runnable()
                            {
                            @Override public void run()
                                {
                                build();
                                }
                            });
                        }
                    });
                }
            });
        RobotLog.vv(TAG, "...ctor()");
        }

    public void close()
        {
        RobotLog.vv(TAG, "close()");
        synchronized (startStopLock)
            {
            if (controlObserver != null)
                {
                controlObserver.close();
                controlObserver = null;
                }
            }
        }

    //----------------------------------------------------------------------------------------------
    // Control
    //----------------------------------------------------------------------------------------------

    /**
     * The creation or updating of the build request file in the control directory will cause us to
     * initiate the whole cascade of processing
     */
    protected synchronized void build()
        {
        trace("build()", new Runnable() { @Override public void run()
            {
            try {
                diagnosticListener = new OnBotJavaDiagnosticsListener(srcDir);
                try {
                    buildStatus = BuildStatus.RUNNING;
                    writeBuildStatusFile(buildStartedFile, "build started");
                    try {
                        clean();
                        ensureBuildDirs();
                        if (compileJavaFiles())
                            {
                            // We can't reuse cached locations or we seem to always crash the vm. So we uniquify.
                            // We associate the uniqueness with the consolidated file itself rather than with
                            // an *opening* of same so as to uniquify on the semantic contents, saving files.
                            // We don't RELY on the date here for uniqueness in any way. But putting it in the build
                            // dir name can, when the date is stable, help keep track of what's what when debugging.
                            SimpleDateFormat formatter =  new SimpleDateFormat("yyyy-MM-dd+HH.mm.ss", Locale.US);
                            File onBotJavaDirDirectory = AppUtil.getInstance().createTempDirectory("onBotJavaJar-" + formatter.format(new Date()) + "-", "", jarsOutputDir);
                            ReadWriteFile.writeFile(currentOnBotJavaDirFile, onBotJavaDirDirectory.getAbsolutePath()); // abspath contains jarsOutputDir dependence to here
                            consolidateClassFilesToJar(onBotJavaDirDirectory);
                            dexifyJarFiles(onBotJavaDirDirectory);
                            OnBotJavaBackupManager.archiveSourceForBuild();
                            buildStatus = BuildStatus.SUCCESSFUL;
                            writeBuildStatusFile(OnBotJavaHelper.buildSuccessfulFile, "last successful build finished");
                            RobotLog.vv(TAG, "onBotJava build finished successfully");
                            }
                        }
                    catch (RuntimeException ex)
                        {
                        RobotLog.ee(TAG, ex, "OnBotJava build failed due to an exception");
                        }
                    finally
                        {
                        buildStatus = buildStatus == BuildStatus.SUCCESSFUL ? BuildStatus.SUCCESSFUL : BuildStatus.FAILED;
                        writeBuildStatusFile(buildCompleteFile, "build completed");
                        }
                    }
                finally
                    {
                    diagnosticListener.flush();
                    diagnosticListener.close();
                    diagnosticListener = null;
                    }
                }
            catch (IOException e)
                {
                // TODO: what better to do here?
                RobotLog.logStackTrace(e);
                }
             catch (RuntimeException ex) // any exception at this point in the application will be fatal, this will be most likely a DexException
                {
                // possible recovery steps might to be cleaning the build directory
                // and re-trying this build, however right now that could become an infinite
                // recursion loop
                // However bad build directory files will be left, leaving errors in
                // ClassLoader (which thankfully we are guarding against)
                RobotLog.ee(TAG, ex, "Error occurred probably while dex-ing OnBotJava build, suggest cleaning build folder and trying again");
                }
            }});
        }

    protected void writeBuildStatusFile(File file, String message)
        {
        // First line of file is timestamp of occurrence. Second line
        // is a brief human-readable description of action, which ought
        // to be redundant with the identity of the file in which the
        // message is stored.
        String contents = String.format(Locale.getDefault(), "%s\n%s\n", AppUtil.getInstance().getIso8601DateTimeFormatter().format(LocalDateTime.now().atOffset(ZoneOffset.UTC)), message);
        ReadWriteFile.writeFile(file, contents);
        }

    protected void clean()
        {
        trace("clean()", new Runnable()
            {
            @Override public void run()
                {
                // Try to start with a clean slate. This is a little tricky, for an odd reason:
                // if, having successfully built and loaded, if we delete certain files then
                // we can't seem to manage to build a second time. So just deleting all the children
                // of the buildDir won't work; we have to be smarter. We don't *exactly* understand
                // what's going on, but we don't want to put more work into it.
                AppUtil.getInstance().delete(classesOutputDir);
                AppUtil.getInstance().delete(sourceOutputDir);

                // Leave the most recently built stuff so that the loader will find it
                File curDir = getCurrentOutputDir();
                for (File child : AppUtil.getInstance().filesIn(jarsOutputDir))
                    {
                    if (!child.equals(curDir))
                        {
                        AppUtil.getInstance().delete(child);
                        }
                    }
                }
            });
        }

    protected boolean compileJavaFiles()
        {
        return trace("compileJavaFiles()", new Supplier<Boolean>() { @Override public Boolean get()
            {
            OnBotJavaCompiler javaCompiler = new OnBotJavaCompiler();
            return javaCompiler.compile(srcDir, diagnosticListener);
            }});
        }

    protected void consolidateClassFilesToJar(final File onBotJavaDirDirectory) throws IOException
        {
        trace("consolidateClassFilesInJar()", new ThrowingCallable<Void, IOException>()
            {
            @Override public Void call() throws IOException
                {
                File onBotJavaJar = new File(onBotJavaDirDirectory, onBotJavaJarName);
                AppUtil.getInstance().ensureDirectoryExists(onBotJavaDirDirectory, false);

                RobotLog.vv(TAG, "consolidating to %s", onBotJavaJar.getPath());
                OutputStream outputStream = new FileOutputStream(onBotJavaJar);
                try {
                    Manifest manifest = new Manifest();
                    JarOutputStream jarOutputStream = new JarOutputStream(outputStream, manifest);
                    try {
                        for (File classFile : AppUtil.getInstance().filesUnder(classesOutputDir, ".class"))
                            {
                            ZipEntry ze = new ZipEntry(AppUtil.getInstance().getRelativePath(classesOutputDir, classFile).getPath());
                            ze.setTime(classFile.lastModified());
                            jarOutputStream.putNextEntry(ze);
                            AppUtil.getInstance().copyStream(classFile, jarOutputStream);
                            jarOutputStream.closeEntry();
                            }
                        }
                    finally
                        {
                        jarOutputStream.close();
                        }
                    }
                finally
                    {
                    outputStream.close();
                    }
                return null;
                }
            });
        }

    protected void dexifyJarFiles(final File onBotJavaDirDirectory) throws IOException
        {
        trace("dexifyJarFiles()", new ThrowingCallable<Void, IOException>()
            {
            @Override public Void call() throws IOException
                {
                File robotJavaJar = null;
                for (File jarFile : getOutputFiles(onBotJavaDirDirectory, ".jar"))
                    {
                    // Sanity check
                    if (jarFile.getName().equals(onBotJavaJarName))
                        {
                        if (robotJavaJar != null)
                            {
                            RobotLog.ee(TAG, "two consolidators: %s %s", robotJavaJar.getPath(), jarFile.getPath());
                            }
                        robotJavaJar = jarFile;
                        }

                    // Actually do the work
                    dexifyJarFile(jarFile);
                    }
                return null;
                }
            });
        }

    public static List<File> getOutputJarFiles()
        {
        return getOutputFiles(null, ".jar");
        }
    public static List<File> getOutputDexFiles()
        {
        return getOutputFiles(null, ".dex");
        }
    private static List<File> getOutputFiles(@Nullable File onBotJavaDirDirectory, String extension)
        {
        List<File> result = new ArrayList<File>();

        // Because of dir/file 'busy' issues having to do with class (re)loading, we use a jar in a new
        // sub dir each and every time. Only the most recent such subdir is relevant. This will
        // be the lexically greatest, since we use a time-based naming scheme.
        File currentOutputDir = getCurrentOutputDir();
        if (currentOutputDir != null)
            {
            if (onBotJavaDirDirectory != null)
                {
                Assert.assertTrue(currentOutputDir.equals(onBotJavaDirDirectory)); // sanity check
                }
            result.addAll(AppUtil.getInstance().filesIn(currentOutputDir, extension));
            }

        return result;
        }

    private static @Nullable File getCurrentOutputDir()
        {
        // Because of dir/file 'busy' issues having to do with class (re)loading, we use a jar etc in a new
        // sub dir each and every time. Only the most recent such subdir is relevant. Be robust about
        // dealing with wonky file system state.
        if (currentOnBotJavaDirFile.exists())
            {
            try {
                String currentOnBotJavaDirName = ReadWriteFile.readFile(currentOnBotJavaDirFile);
                File currentOnBotJavaDir = new File(currentOnBotJavaDirName);
                if (currentOnBotJavaDir.isDirectory())
                    {
                    return currentOnBotJavaDir;
                    }
                }
            catch (RuntimeException e)
                {
                // ignore
                }
            }
        RobotLog.vv(TAG, "getCurrentOutputDir() unavailable");
        return null;
        }

    public static BuildStatus getBuildStatus()
        {
        return buildStatus;
        }

    protected void dexifyJarFile(File jarFile) throws RuntimeException
        {
        try
            {
            File dexFileParent = jarFile.getParentFile();
            dexifyFiles(Collections.singleton(jarFile), dexFileParent, diagnosticListener);
            }
        catch (RuntimeException e)
            {
            RobotLog.ee(TAG, e, "Cannot finish OBJ build. Dex failed");
            throw e;
            }
        }

    static void dexifyFiles(Collection<File> inputFiles, final File dexFileParent,
            OnBotJavaDiagnosticsListener diagnosticListener)
        {
        if (!dexFileParent.isDirectory())
            {
            throw new IllegalArgumentException("dexFileParent must be a directory " + dexFileParent.getAbsolutePath());
            }
        StringBuilder sb = new StringBuilder();
        for (File inputFile : inputFiles)
            {
            sb.append(" " + inputFile.getAbsolutePath());
            }
        RobotLog.vv(TAG, "dexifying %s...", sb.toString());
        // Use a zip file in a temp subdirectory for the output.
        File tmpDir = new File(dexFileParent, UUID.randomUUID().toString());
        tmpDir.mkdir();
        File tmpZipFile = new File(tmpDir, "out.zip");
        try {
            RobotLog.vv(TAG, "using d8");
            D8Command.Builder d8CommandBuilder = D8Command.builder(new D8DiagnosticsHandler())
                .setProgramConsumer(new AndroidDexIndexedConsumer(tmpZipFile));
            // For the classpath, first add the ftcClassPathLibs, then the external libraries, then
            // finally the platformClassPathLibs.
            OrderedClassFileResourceProvider classpathResourceProvider = new OrderedClassFileResourceProvider();
            for (String filename : ftcClassPathLibs)
                {
                File file = new File(OnBotJavaManager.libDir, filename);
                classpathResourceProvider.addClassFileResourceProvider(
                    new ArchiveClassFileResourceProvider(file));
                }
            for (File file : ExternalLibraries.getInstance().getClasspathFiles())
                {
                classpathResourceProvider.addClassFileResourceProvider(
                    new ArchiveClassFileResourceProvider(file));
                }
            for (String filename : platformClassPathLibs)
                {
                File file = new File(OnBotJavaManager.libDir, filename);
                classpathResourceProvider.addClassFileResourceProvider(
                    new ArchiveClassFileResourceProvider(file));
                }
            if (!classpathResourceProvider.isEmpty())
                {
                d8CommandBuilder.addClasspathResourceProvider(classpathResourceProvider);
                }
            // Add the input files.
            for (File inputFile : inputFiles)
                {
                d8CommandBuilder.addProgramResourceProvider(
                    new ArchiveProgramResourceProvider(inputFile));
                }
            D8.run(d8CommandBuilder.build());
            classpathResourceProvider.close();

            // Unpack the output zip to get the .dex files.
            unpackZipFile(tmpZipFile, tmpDir);
            for (File tmpDexFile : AppUtil.getInstance().filesUnder(tmpDir, ".dex"))
                {
                String name = tmpDexFile.getName();
                File destDexFile = new File(dexFileParent, name);
                if (!tmpDexFile.renameTo(destDexFile))
                    {
                    throw new RuntimeException("failed to move " +
                        tmpDexFile.getAbsolutePath() + " to " + destDexFile.getAbsolutePath());
                    }
                RobotLog.vv(TAG, "Wrote %s", destDexFile.getAbsolutePath());
                }
            }
        catch (CompilationFailedException e)
            {
            diagnosticListener.getPrintStream().format(diagnosticListener.locale, "dex: CompilationFailedException: %s", e.getMessage());
            throw new RuntimeException(e);
            }
        catch (IOException e)
            {
            diagnosticListener.getPrintStream().format(diagnosticListener.locale, "dex: IOException: %s", e.getMessage());
            throw new RuntimeException(e);
            }
        catch (RuntimeException e)
            {
            diagnosticListener.getPrintStream().format(diagnosticListener.locale, "dex: RuntimeException: %s", e.getMessage());
            throw e;
            }
        finally
            {
            AppUtil.getInstance().delete(tmpDir);
            RobotLog.vv(TAG, "...dexifying %s", sb.toString());
            }
        }

    static void unpackZipFile(File file, File destDir) throws IOException
        {
        ZipFile zipFile = new ZipFile(file);
        try
            {
            for (Enumeration<? extends ZipEntry> entries = zipFile.entries(); entries.hasMoreElements(); )
                {
                ZipEntry entry = entries.nextElement();
                if (entry.isDirectory())
                    {
                    continue;
                    }
                String entryName = entry.getName();

                // Extract the file.
                File destFile = new File(destDir, entryName);
                destFile.getParentFile().mkdirs();
                AppUtil.getInstance().copyStream(zipFile.getInputStream(entry), destFile);
                }
            }
        finally
            {
            zipFile.close();
            }
        }

    /**
     * Returns the ClassLoader that is the parent ClassLoader for every OnBotJavaClassLoader.
     */
    public static ClassLoader getParentClassLoaderForOnBotJava()
        {
        ClassLoader extLibClassLoader = ExternalLibraries.getInstance().getClassLoader();
        return (extLibClassLoader != null)
            ? extLibClassLoader
            : OnBotJavaManager.class.getClassLoader();
        }

    protected <VALUE, EXCEPTION_T extends IOException> VALUE trace(String message, ThrowingCallable<VALUE, EXCEPTION_T> callable) throws IOException
        {
        pretrace(message);
        try {
            return callable.call();
            }
        finally
            {
            posttrace(message);
            }
        }

    protected void trace(String message, Runnable runnable)
        {
        pretrace(message);
        try {
            runnable.run();
            }
        finally
            {
            posttrace(message);
            }
        }

    protected <T> T trace(String message, Supplier<T> supplier)
        {
        pretrace(message);
        try {
            return supplier.get();
            }
        finally
            {
            posttrace(message);
            }
        }

    protected void pretrace(String message)
        {
        RobotLog.vv(TAG, "%s...", message);
        testBusy(message, "pre");
        }

    protected void posttrace(String message)
        {
        testBusy(message, "post");
        RobotLog.vv(TAG, "...%s", message);
        }

    protected void testBusy(String message, String phase)
        {
        File file = new File(jarsOutputDir, "testBusy.txt");
        try {
            ReadWriteFile.writeFileOrThrow(file, message);
            }
        catch (IOException e)
            {
            RobotLog.ee(TAG, e, "%s: %s: %s is now busy", message, phase, jarsOutputDir);
            }
        finally
            {
            file.delete();
            }
        }
    }
