package com.quancheng.achilles.dao.model;

import java.util.List;

public class Menu {
    private Long id;
    private String menuName;
    private String url;
    private List<Menu> child;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getMenuName() {
        return menuName;
    }
    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public List<Menu> getChild() {
        return child;
    }
    public void setChild(List<Menu> child) {
        this.child = child;
    }
}
