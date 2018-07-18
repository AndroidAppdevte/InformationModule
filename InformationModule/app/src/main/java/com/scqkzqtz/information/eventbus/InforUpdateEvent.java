package com.scqkzqtz.information.eventbus;

import java.util.Date;

/**
 * Created by hghl on 2017/6/21.
 */

public class InforUpdateEvent {
    private String Tag = "";
    private Date date;

    public InforUpdateEvent(String tag, Date date) {
        this.Tag = tag;
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTag() {
        return Tag;
    }

    public void setTag(String tag) {
        Tag = tag;
    }

}
