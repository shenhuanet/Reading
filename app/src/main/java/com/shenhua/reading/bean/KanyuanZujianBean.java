package com.shenhua.reading.bean;

import java.io.Serializable;

/**
 * Created by shenhua on 3/31/2016.
 */
public class KanyuanZujianBean implements Serializable {

    private static final long serialVersionUID = 3048871915902916490L;
    private String title;
    private String from;
    private String describe;
    private String otherInfo;
    private String urlDetail;
    private String urlImg;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String decribe) {
        this.describe = decribe;
    }

    public String getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo;
    }

    public String getUrlDetail() {
        return urlDetail;
    }

    public void setUrlDetail(String urlDetail) {
        this.urlDetail = urlDetail;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }
}
