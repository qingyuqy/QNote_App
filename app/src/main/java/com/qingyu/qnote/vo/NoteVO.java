package com.qingyu.qnote.vo;

/**
 * Created by QingYu on 2017/2/28 0028.
 */

public class NoteVO {

    public long key;
    private String title;
    private String content;
    private String sign;
    private long date;

    public long getKey() {
        return key;
    }

    public void setKey(long key) {
        this.key = key;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
