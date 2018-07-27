package com.it.util;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * 自定义的文件查看器
 */
public class CustomFileVisitor implements FileVisitor<Path> {

    private Path tf = null;
    private Integer level = 0;

    public CustomFileVisitor(){

    }

    public CustomFileVisitor(Path target) {
        this.tf = Paths.get(target.toString());
    }

    //退出目录
    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        tf = tf.getParent();
        Files.delete(dir);
        level--;
        return FileVisitResult.CONTINUE;
    }

    //进入目录前
    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        if (level != 0) {
            tf = tf.resolve(dir.getFileName().toString());
        }
        if (!Files.exists(tf)) Files.createDirectories(tf);
        level++;
        return FileVisitResult.CONTINUE;
    }

    //读取文件
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        Path tff = Paths.get(tf.toAbsolutePath().toString(), file.getFileName().toString());
        if (Files.exists(tff)) Files.delete(tff);
        Files.move(file, tff);
        return FileVisitResult.CONTINUE;
    }

    //读取文件失败
    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }

}
