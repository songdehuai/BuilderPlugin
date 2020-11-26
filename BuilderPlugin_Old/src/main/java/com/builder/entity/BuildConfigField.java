package com.builder.entity;


import com.builder.utils.ReplaceTools;

public class BuildConfigField {

    private String type;
    private String key;
    private String value;


    public BuildConfigField(String type, String key, String value) {
        this.type = ReplaceTools.replace(type);
        this.key = ReplaceTools.replace(key);
        this.value = ReplaceTools.replace(value);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


}
