package com.scqkzqtz.information.entity;

import java.util.Date;
import java.util.List;

public class CommonInforEntity {

    private String  title;
    private String[] tag;
    private String publishTime;
    private String thumbnail;
    private List<InforImageEntity> inforImageEntities;

    //for common infor
    public CommonInforEntity(String title, String[] tag, String publishTime, String thumbnail) {
        this.title = title;
        this.tag = tag;
        this.publishTime = publishTime;
        this.thumbnail = thumbnail;
    }
    //for common theme infor
    public CommonInforEntity(String title, String[] tag, String publishTime, List<InforImageEntity> inforImageEntities) {
        this.title = title;
        this.tag = tag;
        this.publishTime = publishTime;
        this.inforImageEntities = inforImageEntities;
    }

    //for common text infor
    public CommonInforEntity(String title, String[] tag, String publishTime) {
        this.title = title;
        this.tag = tag;
        this.publishTime = publishTime;
    }

    public List<InforImageEntity> getInforImageEntities() {
        return inforImageEntities;
    }

    public void setInforImageEntities(List<InforImageEntity> inforImageEntities) {
        this.inforImageEntities = inforImageEntities;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getTag() {
        return tag;
    }

    public void setTag(String[] tag) {
        this.tag = tag;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
