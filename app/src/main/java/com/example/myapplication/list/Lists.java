package com.example.myapplication.list;

import java.util.Date;

public class Lists {
    public String name;
    public String color;
    public Boolean includeMap;
    public String icon;
    public Date lastUpdate;

    public Lists(String name, String color, Boolean includeMap, String icon, Date lastUpdate) {
        this.name = name;
        this.color = color;
        this.includeMap = includeMap;
        this.icon = icon;
        this.lastUpdate = lastUpdate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setIncludeMap(Boolean includeMap) {
        this.includeMap = includeMap;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public Boolean getIncludeMap() {
        return includeMap;
    }

    public String getIcon() {
        return icon;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }
}
