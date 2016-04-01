package com.shenhua.reading.bean;

import java.io.Serializable;

/**
 * Created by shenhua on 4/1/2016.
 */
public class KaifazheBean implements Serializable {

    private static final long serialVersionUID = 1526887708421932443L;
    private String title;
    private String derecit;
    private String comment;
    private String from;
    private String img_url;
    private String thumb;
    private String href;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDerecit() {
        return derecit;
    }

    public void setDerecit(String derecit) {
        this.derecit = derecit;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
