package com.it.client.util;

import org.apache.commons.codec.digest.DigestUtils;
import sun.net.ftp.FtpClient;
import sun.net.ftp.FtpDirEntry;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

/**
 * FTP上传下载模块
 * 操作本地文件不能用NIO，否则会占用文件。
 */
public class FtpUtil {

    private static FtpClient ftp = FtpClient.create();

    public static boolean connect(String host, Integer port, String userName, String password) throws Exception {
        boolean b = false;
        try {
            ftp.connect(new InetSocketAddress(host, port));
            ftp.login(userName, password.toCharArray());
            b = true;
        } catch (Exception e) {
            throw new Exception("连接文件服务器错误");
        }
        return b;
    }

    public static Boolean exist(String path) {
        Boolean res = false;
        try {
            ftp.getLastModified(path);
            res = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public static Boolean isFile(String path) {
        Boolean res = false;
        try {
            ftp.getSize(path);
            res = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public static Boolean isDir(String path) {
        Boolean res = false;
        try {
            ftp.changeDirectory(path);
            res = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public static void close() {
        try {
            ftp.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //计算远程文件大小
    public static Long countRemoteFileSize(String remotePath) {
        return countRemoteFileSize(Paths.get(remotePath));
    }

    public static Long countRemoteFileSize(Path remotePath) {
        Long l = 0L;
        try {
            if (isDir(remotePath.toString())) {
                Iterator<FtpDirEntry> files = ftp.listFiles(remotePath.toString());
                while (files.hasNext()) {
                    l += countRemoteFileSize(remotePath.resolve(files.next().getName()));
                }
            } else {
                l = ftp.getSize(remotePath.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return l;
    }

    //计算本地文件大小
    public static Long countLocalFileSize(String localPath) {
        return countLocalFileSize(new File(localPath));
    }

    public static Long countLocalFileSize(File f) {
        Long l = 0L;
        try {
            if (f.isDirectory()) {
                for (File file : f.listFiles()) {
                    l += countLocalFileSize(file);
                }
            } else {
                l = f.length();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return l;
    }

    public static void upLoad(String localPath, String remotePath) throws Exception {
        ftp.setBinaryType();
        Long totalSize = countLocalFileSize(localPath);
        doUpLoad(localPath, remotePath);
    }

    private static Boolean doUpLoad(String localPath, String remotePath) throws Exception {
        Boolean res = false;
        if (!exist(Paths.get(remotePath).getParent().toString())) {
            ftp.makeDirectory(Paths.get(remotePath).getParent().toString());
        }
        File f = new File(localPath);
        if (f.isDirectory()) {
            if (!isDir(remotePath)) ftp.makeDirectory(remotePath);
            for (File file : f.listFiles()) {
                Boolean r = false;
                while (!r) {
                    r = doUpLoad(file.getPath(),
                            Paths.get(remotePath).resolve(file.getName()).toString());
                }
            }
            res = true;
        } else {
            OutputStream os = ftp.putFileStream(remotePath);
            InputStream is = new FileInputStream(f);
            byte[] buff = new byte[150000];
            int s;
            while ((s = is.read(buff)) >= 0) {
                if (s > 0) {
                    os.write(buff, 0, s);
                }
            }
            os.close();
            is.close();
            res = true;
        }
        return res;
    }

    public static void downLoad(String remotePath, String localPath) throws Exception {
        ftp.setBinaryType();
        Long totalSize = countRemoteFileSize(remotePath);
        doDownLoad(remotePath, localPath);
    }

    private static Boolean doDownLoad(String remotePath, String localPath) throws Exception {
        if (Files.notExists(Paths.get(localPath).getParent()))
            Files.createDirectories(Paths.get(localPath).getParent());
        Boolean res = false;
        try {
            if (isDir(remotePath)) {
                Files.createDirectories(Paths.get(localPath));
                Iterator<FtpDirEntry> iterator = ftp.listFiles(remotePath);
                while (iterator.hasNext()) {
                    res = doDownLoad(
                            Paths.get(remotePath).resolve(iterator.next().getName()).toString(),
                            Paths.get(localPath).resolve(iterator.next().getName()).toString()
                    );
                }
            } else {
                File localFile = new File(localPath);
                if (localFile.exists()) {
                    Long ls = localFile.length();
                    Long rs = countRemoteFileSize(remotePath);
                    if (ls.equals(rs)) {
                        InputStream ii = new FileInputStream(localFile);
                        try {
                            String md5l = DigestUtils.md5Hex(ii);
                            String md5r = DigestUtils.md5Hex(remotePath);
                            if (md5l.equals(md5r)) {
                                res = true;
                                return res;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            if (ii != null) {
                                ii.close();
                            }
                        }
                    }
                }
                InputStream is = ftp.getFileStream(remotePath);
                OutputStream os = new FileOutputStream(new File(localPath));
                byte[] buff = new byte[150000];
                int s;
                while ((s = is.read(buff)) >= 0) {
                    if (s > 0) {
                        os.write(buff, 0, s);
                    }
                }
                os.close();
                is.close();
                res = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            res = false;
        }
        return res;
    }

}
