package com.builder.entity;

import com.google.gson.Gson;

import java.util.List;

/**
 * 程序配置文件
 */
public class Config {

    /**
     * channelFilePath : /Users/songdehuai/Desktop/files
     * channelListFile : /Users/songdehuai/Desktop/channelListFile.txt
     * defaultGradleFile : /BuilderConfig/default.build.gradle
     * outputFile : /Users/songdehuai/Desktop/apks
     */

    private String channelFilePath;
    private String channelListFile;
    private String defaultGradleFile;
    private String outputFile;
    private String channelJsonData;

    private List<Files> filesList;

    public List<Files> getFilesList() {
        return filesList;
    }

    public void setFilesList(List<Files> filesList) {
        this.filesList = filesList;
    }

    public String getChannelJsonData() {
        return channelJsonData;
    }

    public void setChannelJsonData(String channelJsonData) {
        this.channelJsonData = channelJsonData;
    }

    public String getChannelFilePath() {
        return channelFilePath;
    }

    public void setChannelFilePath(String channelFilePath) {
        this.channelFilePath = channelFilePath;
    }

    public String getChannelListFile() {
        return channelListFile;
    }

    public void setChannelListFile(String channelListFile) {
        this.channelListFile = channelListFile;
    }

    public String getDefaultGradleFile() {
        return defaultGradleFile;
    }

    public void setDefaultGradleFile(String defaultGradleFile) {
        this.defaultGradleFile = defaultGradleFile;
    }

    public String getOutputFile() {
        return outputFile;
    }

    public void setOutputFile(String outputFile) {
        this.outputFile = outputFile;
    }


    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
