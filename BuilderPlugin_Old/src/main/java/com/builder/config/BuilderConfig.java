package com.builder.config;

import com.builder.entity.Config;
import com.builder.entity.Files;
import com.builder.utils.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class BuilderConfig {

    public final static String DEFAULT_CHARSET_NAME = "UTF-8";

    public static final String GROUP_NAME = "builder";

    public static final String TEMP_FILE = ".builder/";

    public static final String GRADLE_FILE_NAME = "/build.gradle";

    public static final String RESULT_FILE_NAME = "_RESULT.txt";

    private static BuilderConfig builderConfig = new BuilderConfig();


    public static BuilderConfig getInstance() {
        return builderConfig;
    }

    private BuilderConfig() {
    }

    private Config config;
    private boolean isInit = false;
    private String configFilePath = "BuilderConfig/config.json";
    private String parentFilePath = "";

    public void setConfig(String configFilePath) {
        parentFilePath = configFilePath;
        this.configFilePath = (configFilePath += "/BuilderConfig/config.json");
        initConfig();
    }

    private void initConfig() {
        File jsonFile = new File(configFilePath);
        if (!jsonFile.exists()) {
            Log.e(configFilePath + " 不存在");
            System.exit(1);
        }
        try {
            String json = FileUtils.readFileToString(jsonFile, DEFAULT_CHARSET_NAME);
            config = new Gson().fromJson(json, new TypeToken<Config>() {
            }.getType());
            Log.i("初始化配置信息:" + config.toString());
            isInit = true;
        } catch (IOException e) {
            e.printStackTrace();
            isInit = false;
        }
    }

    public String DEFAULT_BUILD_GRADLE() {
        if (isInit) {
            return parentFilePath + config.getDefaultGradleFile();
        } else {
            return "";
        }
    }

    public String FLAVOR_FILES() {
        if (isInit) {
            return parentFilePath + config.getChannelFilePath();
        } else {
            return "";
        }
    }

    public String FLAVOR_LIST_FILE() {
        if (isInit) {
            return parentFilePath + config.getChannelListFile();
        } else {
            return "";
        }
    }


    public String CHANNEL_DATA_PATH() {
        if (isInit) {
            return parentFilePath + config.getChannelJsonData();
        } else {
            return "";
        }
    }

    public String OUTPUT_FILE_PATH() {
        if (isInit) {
            return parentFilePath + config.getOutputFile();
        } else {
            return "";
        }
    }

    public List<Files> FILES() {
        if (isInit) {
            return config.getFilesList();
        } else {
            return null;
        }
    }
}
