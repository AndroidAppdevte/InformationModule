package com.scqkzqtz.information.entity;

import java.util.Date;

import io.realm.RealmModel;

/**
 * Created by hghl on 2017/5/23.
 */

public class DBOperationSite implements RealmModel {
    private String desc;
    private long isDisable;
    private Date startTime;
    private Date endTime;
    private String type;
    private String title;
    private String thumbnail;
    private String url;
    private String sn;
    private String platform;
    private int version;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public long getIsDisable() {
        return isDisable;
    }

    public void setIsDisable(long isDisable) {
        this.isDisable = isDisable;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}

