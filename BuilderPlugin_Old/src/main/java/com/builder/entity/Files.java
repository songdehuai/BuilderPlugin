package com.builder.entity;

import java.io.File;

public class Files {

    public Files() {

    }

    public Files(String fromFile, String toFile, String desc) {
        this.fromFile = fromFile;
        this.toFile = toFile;
        this.desc = desc;
    }

    private String fromFile;
    private String toFile;
    private String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getFromFile() {
        return fromFile;
    }

    public void setFromFile(String fromFile) {
        this.fromFile = fromFile;
    }

    public String getToFile() {
        return toFile;
    }

    public void setToFile(String toFile) {
        this.toFile = toFile;
    }
}
