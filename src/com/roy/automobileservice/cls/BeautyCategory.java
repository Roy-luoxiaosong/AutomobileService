package com.roy.automobileservice.cls;

import cn.bmob.v3.BmobObject;

/**
 * Created by Roy on 2016/5/11.
 */
public class BeautyCategory extends BmobObject {
    private String name;
    private String content;

    public BeautyCategory(){}
    public BeautyCategory(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

