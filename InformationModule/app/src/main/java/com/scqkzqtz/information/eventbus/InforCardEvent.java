package com.scqkzqtz.information.eventbus;

import java.util.List;

/**
 * Created by hghl on 2017/6/2.
 */

public class InforCardEvent {
    private String Tag = "";
    private List<String> strList;

//    public InforCardEvent(String tag, List<String>  strList) {
//        this.Tag = tag;
//        this.strList = strList;
//    }

    public InforCardEvent(String tag, List<String> strList) {
        Tag = tag;
        this.strList = strList;
    }

    public List<String> getStrList() {
        return strList;
    }

    public void setStrList(List<String> strList) {
        this.strList = strList;
    }

    public String getTag() {
        return Tag;
    }

    public void setTag(String tag) {
        Tag = tag;
    }

}
