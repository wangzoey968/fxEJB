package com.it.client.order.cus;

import com.it.api.table.order.Tb_Order;
import com.it.client.EJB;
import javafx.beans.property.SimpleDoubleProperty;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Bean_MakeOrder {

    //创建订单
    private Tb_Order order = new Tb_Order();

    public Tb_Order getOrder() {
        return order;
    }

    public void setOrder(Tb_Order order) {
        this.order = order;
    }

    //设置所属的文件
    private File file = null;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
        this.totalSize.set(file.length() / 1024 / 1024);
    }

    //标记该订单的状态
    private Integer orderStatus = null;

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    //标记订单的上传进度
    private SimpleDoubleProperty uploadedSize = new SimpleDoubleProperty();
    private SimpleDoubleProperty totalSize = new SimpleDoubleProperty();

    public double getUploadedSize() {
        return uploadedSize.get();
    }

    public SimpleDoubleProperty uploadedSizeProperty() {
        return uploadedSize;
    }

    public void setUploadedSize(double uploadedSize) {
        this.uploadedSize.set(uploadedSize);
    }

    public double getTotalSize() {
        return totalSize.get();
    }

    public SimpleDoubleProperty totalSizeProperty() {
        return totalSize;
    }

    public void setTotalSize(double totalSize) {
        this.totalSize.set(totalSize);
    }

    public void uploadFile() {
        FileInputStream ins = null;
        FileOutputStream outs = null;
        try {
            if (file != null) {
                Path path = Paths.get("E:/testFile/");
                if (!Files.exists(path)) {
                    Files.createDirectories(path);
                }
                ins = new FileInputStream(file);
                outs = new FileOutputStream(path + File.separator + file.getName());
                byte[] b = new byte[1024 * 1024];//大小为1M
                int len;
                Long uploaded = 0L;
                while ((len = ins.read(b)) > 0) {
                    this.setOrderStatus(Bean_MakeOrderStatus.UPLOADING);
                    outs.write(b, 0, len);
                    uploaded += len;
                    this.uploadedSize.set(uploaded / 1024 / 1024D);
                }
                EJB.getOrderService().makeOrder(EJB.getSessionId(), this.getOrder());
                this.setOrderStatus(Bean_MakeOrderStatus.UPLOADED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ins != null) {
                    ins.close();
                }
                if (outs != null) {
                    outs.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
