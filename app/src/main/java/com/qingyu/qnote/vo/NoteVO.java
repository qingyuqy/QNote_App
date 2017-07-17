package com.qingyu.qnote.vo;

import java.io.Serializable;

/**
 * Created by QingYu on 2017/2/28 0028.
 */

public class NoteVO implements Serializable {

    public long key;
    public String title;
    public String content;
    public long date;

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

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
