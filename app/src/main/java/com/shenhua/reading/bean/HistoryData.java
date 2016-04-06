package com.shenhua.reading.bean;

import java.io.Serializable;

/**
 * Created by shenhua on 4/6/2016.
 */
public class HistoryData implements Serializable {
    private static final long serialVersionUID = -473304586988435390L;

    private String title, time, url, describe;
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
