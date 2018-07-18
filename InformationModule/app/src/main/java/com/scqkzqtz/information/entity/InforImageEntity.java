package com.scqkzqtz.information.entity;

import java.io.Serializable;

/**
 * Created by hghl on 2017/9/27.
 */

public class InforImageEntity  implements Serializable {

    private String imageText;
    private String imageUrl;

    public InforImageEntity(Object imageText, Object imageUrl) {
        if (imageText == null ){
            this.imageText = "";
        }else if (imageText instanceof Integer) {
            this.imageText = Integer.toString((Integer)imageText);
        }else{
            this.imageText = (String)imageText;
        }
        if (imageUrl == null ){
            this.imageUrl = "";
        }else if (imageUrl instanceof Integer) {
            this.imageUrl = Integer.toString((Integer)imageUrl);
        }else{
            this.imageUrl = (String)imageUrl;
        }
    }

    public String getImageText() {
        return imageText;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
