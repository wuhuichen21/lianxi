package com.xiaowu.crowd.entity.po;

import java.util.ArrayList;
import java.util.List;

public class Menu {
//    主键id
    private Integer id;
//    父节点id
    private Integer pid;
//    节点名称
    private String name;
//    节点附带的url地址
    private String url;
//    节点图标的样式
    private String icon;
//    控制节点是否为打开状态，默认为打开状态
    private Boolean open = true;
//    存储子节点的集合，初始化是为了避免空指针异常
    private List<Menu> children = new ArrayList<>();

    public Menu(Integer id, Integer pid, String name, String url, String icon, Boolean open, List<Menu> children) {
        this.id = id;
        this.pid = pid;
        this.name = name;
        this.url = url;
        this.icon = icon;
        this.open = open;
        this.children = children;
    }
    public Menu(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public List<Menu> getChildren() {
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", pid=" + pid +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", icon='" + icon + '\'' +
                ", open=" + open +
                ", children=" + children +
                '}';
    }
}