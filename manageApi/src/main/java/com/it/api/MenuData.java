package com.it.api;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单数据；
 */
public class MenuData implements Serializable {

    /**
     * 菜单名称；
     */
    private String name;
    /**
     * 功能
     * MENU     :   子菜单；
     * URL      :   打开value对应的URL相对路径
     * FUN      :   功能调用；
     * SEP      :   分割线；
     */
    private String key = "URL";
    /**
     * 功能值
     */
    private String value = "";
    /**
     * 权限，为Null或为空字符串= 不需要权限；
     */
    private String auth;
    /**
     * 子菜单列表；
     */
    private List<MenuData> subMenu;

    public MenuData() {
    }

    public MenuData(String name, String key, String value, String auth, List<MenuData> subMenu) {
        this.name = name;
        this.key = key;
        this.value = value;
        this.auth = auth;
        this.subMenu = subMenu;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public List<MenuData> getSubMenu() {
        return subMenu;
    }

    public void setSubMenu(List<MenuData> subMenu) {
        this.subMenu = subMenu;
    }

    @Override
    public String toString() {
        return "MenuData{" +
                "name='" + name + '\'' +
                ", key='" + key + '\'' +
                ", value='" + value + '\'' +
                ", auth='" + auth + '\'' +
                ", subMenu=" + subMenu +
                '}';
    }
}
