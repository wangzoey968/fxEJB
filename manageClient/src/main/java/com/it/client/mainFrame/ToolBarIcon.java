package com.it.client.mainFrame;

import com.it.client.util.ImgUtil;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ToolBarIcon {

    private PopupMenu popupMenu = new PopupMenu();
    private MenuItem openItem = new MenuItem("Show");
    private MenuItem hideItem = new MenuItem("Hide");
    private MenuItem quitItem = new MenuItem("Quit");

    public SystemTray tray = SystemTray.getSystemTray();
    public TrayIcon trayIcon = null;

    //单例
    private static ToolBarIcon instance = new ToolBarIcon();

    public static ToolBarIcon getInstance() {
        if (instance == null) {
            instance = new ToolBarIcon();
        }
        return instance;
    }

    private ToolBarIcon() {
        popupMenu.add(openItem);
        popupMenu.add(hideItem);
        popupMenu.add(quitItem);
        hideItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            MainFrame.getInstance().hide();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        openItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMainFrame();
            }
        });
        quitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Platform.exit();
            }
        });
        //添加托盘图标
        trayIcon = new TrayIcon(SwingFXUtils.fromFXImage(ImgUtil.IMG_ICON_16X16, null), "Luna", popupMenu);
        trayIcon.setToolTip("工厂客户端");
        trayIcon.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent arg0) {
                if (arg0.getButton() == MouseEvent.BUTTON1) showMainFrame();
            }

            public void mouseEntered(MouseEvent arg0) {
            }

            public void mouseExited(MouseEvent arg0) {
            }

            public void mousePressed(MouseEvent arg0) {
            }

            public void mouseReleased(MouseEvent arg0) {
            }
        });
    }

    boolean show = false;

    public void addIcon() {
        try {
            if (!show) tray.add(trayIcon);
            show = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeIcon() {
        try {
            tray.remove(trayIcon);
            show = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showMainFrame() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    MainFrame frame = MainFrame.getInstance();
                    frame.setIconified(false);
                    frame.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
