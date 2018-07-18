package com.scqkzqtz.information.entity;

import java.io.Serializable;

/**
 * Created by hghl on 2017/9/27.
 *
 */

public class InforEntity implements Serializable {

    private String title;
    private String url;

    private String ObjectId;
    private String ArticleContent_KEY;
    private String ArticleImage_KEY;
    private int collectNumber;
    private int shareNumber;

    public InforEntity(String title, String url, String objectId, String articleContent_KEY, String articleImage_KEY, Object collectNumber, Object shareNumber) {

        this.title = title;
        this.url = url;
        ObjectId = objectId;
        ArticleContent_KEY = articleContent_KEY;
        ArticleImage_KEY = articleImage_KEY;

        if (collectNumber == null) {
            this.collectNumber = 0;
        } else {
            this.collectNumber = Integer.parseInt("" + collectNumber);
        }

        if (shareNumber == null) {
            this.shareNumber = 0;
        } else {
            this.shareNumber = Integer.parseInt("" + shareNumber);
        }
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getObjectId() {
        return ObjectId;
    }

    public String getArticleContent_KEY() {
        return ArticleContent_KEY;
    }

    public String getArticleImage_KEY() {
        return ArticleImage_KEY;
    }

    public int getCollectNumber() {
        return collectNumber;
    }

    public int getShareNumber() {
        return shareNumber;
    }
}
