package com.it.test;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by wangzy on 2018/4/20.
 */
public class FtpTest {
    @Test
    public void ftp(){
        FTPClient ftp = new FTPClient();
        InputStream local=null;
        try {
            //连接ftp服务器
            ftp.connect("192.168.80.161", 21);
            //登录
            ftp.login("ftpuser", "1111");
            //设置上传路径
            String basePath="/home/ftpuser/image/";
            Date currentDate = new Date();
            String dateStr=new SimpleDateFormat("yyyy/MM/dd").format(currentDate);
            for(String pathStr:dateStr.split("/")){
                basePath+=pathStr+"/";
                boolean flag = ftp.changeWorkingDirectory(basePath);
                if(!flag){
                    //创建上传的路径  该方法只能创建一级目录，在这里如果/home/ftpuser存在则可创建image
                    ftp.makeDirectory(basePath);
                }
            }
            //指定上传路径
            ftp.changeWorkingDirectory(basePath);
            //指定上传文件的类型  二进制文件
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            //读取本地文件
            File file = new File("mm.jpg");
            local = new FileInputStream(file);
            //第一个参数是文件名
            ftp.storeFile(getFileName(file.getName()), local);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                //关闭文件流
                local.close();
                //退出
                ftp.logout();
                //断开连接
                ftp.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //生成上传文件的名称
    public String getFileName(String primitiveFileName){
        //使用uuid生成文件名
        String fileName= UUID.randomUUID().toString();
        //获取文件后缀
        String suffix=primitiveFileName.substring(primitiveFileName.lastIndexOf("."));
        return fileName+suffix;
    }

    @Test
    public void ss(){
        System.out.println(this.toString().substring(0,this.toString().indexOf("@")));
    }

}
