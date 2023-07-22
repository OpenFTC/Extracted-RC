package org.firstinspires.ftc.onbotjava.handlers.file;

import com.qualcomm.robotcore.util.ReadWriteFile;
import fi.iki.elonen.NanoHTTPD;
import org.firstinspires.ftc.onbotjava.OnBotJavaFileSystemUtils;
import org.firstinspires.ftc.onbotjava.OnBotJavaManager;
import org.firstinspires.ftc.onbotjava.OnBotJavaProgrammingMode;
import org.firstinspires.ftc.onbotjava.RegisterWebHandler;
import org.firstinspires.ftc.robotcore.internal.webserver.WebHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.*;

@RegisterWebHandler(uri = OnBotJavaProgrammingMode.URI_FILE_GET_TREE)
public class FetchSourceTree implements WebHandler {
    @Override
    public NanoHTTPD.Response getResponse(NanoHTTPD.IHTTPSession session) throws IOException, NanoHTTPD.ResponseException {
        final String randomBoundary = UUID.randomUUID().toString();
        ArrayList<String> srcList = new ArrayList<>();
        final String srcPath = OnBotJavaManager.srcDir.getAbsolutePath();
        File srcDir = OnBotJavaManager.srcDir;
        ReadWriteFile.ensureAllChangesAreCommitted(srcDir);
        OnBotJavaFileSystemUtils.searchForFiles(srcPath, srcDir, srcList, true);

        List<InputStream> sourceFiles = new ArrayList<>();
        sourceFiles.add(new ByteArrayInputStream((MessageFormat.format("\nBOUNDARY {0}\n", randomBoundary)).getBytes(StandardCharsets.UTF_8)));
        for (String sourceFile : srcList) {
            File file = new File(srcPath + sourceFile);
            if (file.canRead() && !file.isDirectory()) {
                FileInputStream fileInputStream = new FileInputStream(srcPath + sourceFile);
                InputStream inputStream = fileNamedChunkStream(randomBoundary, sourceFile, fileInputStream);
                sourceFiles.add(inputStream);
            }
        }
        InputStream files = new SequenceInputStream(Collections.enumeration(sourceFiles));
        return NanoHTTPD.newChunkedResponse(NanoHTTPD.Response.Status.OK, NanoHTTPD.MIME_PLAINTEXT, files);
    }

    public InputStream fileNamedChunkStream(String boundary, String fileName, FileInputStream fileInputStream) {
        InputStream inputStream = new ByteArrayInputStream((MessageFormat.format("\nBOUNDARY {0} {1}\n", boundary, fileName)).getBytes(StandardCharsets.UTF_8));
        return new SequenceInputStream(inputStream, fileInputStream);
    }
}
