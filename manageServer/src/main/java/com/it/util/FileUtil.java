package com.it.util;

import org.junit.Test;
import org.springframework.util.FileCopyUtils;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtil {

    public static void openFile(String filePathStr) throws IOException {
        if (isRemoteFile(filePathStr)) {
            //new DownLoadFileStage(filePathStr, Paths.get(System.getProperty("user.dir") + "/cache/" + filePathStr).toString(), true).start();
            int filSize = FileCopyUtils.copy(Paths.get(filePathStr).toFile(), Paths.get("C:\\Users\\Administrator\\Desktop\\" + Paths.get(filePathStr).getFileName()).toFile());
        } else {
            Desktop.getDesktop().open(Paths.get(filePathStr).toFile());
        }
    }

    public static void browseFile(String filePathStr) throws IOException {
        if (isRemoteFile(filePathStr)) {
            Path fileName = Paths.get(filePathStr).getFileName();
            FileCopyUtils.copy(Paths.get("/file/" + filePathStr).toFile(), Paths.get("C:\\Users\\Administrator\\Desktop\\" + fileName).toFile());
        } else {
            String cmd = "Explorer /SELECT,\"${filePathStr}\"";
            Runtime.getRuntime().exec(cmd);
        }
    }

    public static Boolean isRemoteFile(String filePathStr) {
        return filePathStr.startsWith("/file") || filePathStr.startsWith("\\file");
    }

    public static void movePath(Path source, Path target) throws Exception {
        if (Files.isDirectory(source)) {
            Files.walkFileTree(source, new CustomFileVisitor(target));
        } else {
            if (!Files.exists(target.getParent())) Files.createDirectories(target.getParent());
            Files.move(source, target);
        }
    }

    // 打开其他任意格式的文件，比如txt,word等
    @Test
    public void openFile() {
        final Runtime runtime = Runtime.getRuntime();
        String filePath="C:\\download\\wzyDesktop\\bug.txt";
        Process process = null;
        String cmd="notepad "+filePath;
        System.setProperty("user.dir","112233");
        try {
            process = runtime.exec(cmd);
        } catch (final Exception e) {
            System.out.println("Error exec!");
        }
    }

}
