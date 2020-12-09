package com.builder.task;

import com.builder.config.BuilderConfig;
import com.builder.entity.Config;
import com.builder.entity.Files;
import com.builder.utils.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.commons.io.FileUtils;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


class ConfigInitTask extends DefaultTask {


    String projectPath;

    public ConfigInitTask() throws IOException {
        setGroup(BuilderConfig.GROUP_NAME);
        projectPath = getProject().getParent().getProjectDir().toString();
    }

    public void init() throws IOException {
        File configJsonFile = new File(projectPath + "/BuilderConfig/config.json");
        if (!configJsonFile.exists()) {
            Log.log("配置文件不存在,生成配置文件:" + configJsonFile.getAbsolutePath());
            FileUtils.touch(configJsonFile);
            FileUtils.write(configJsonFile, getPrettyJson(getNewConfig()), BuilderConfig.DEFAULT_CHARSET_NAME, false);
        } else {
            Log.log("配置文件已存在:" + configJsonFile.getAbsolutePath());
        }
        //备份app/build.gradle
        File buildGradleFile = new File(projectPath + "/app/build.gradle");
        File defaultBuildGradle = new File(projectPath + "/BuilderConfig/default.build.gradle");
        if (buildGradleFile.exists()) {
            FileUtils.touch(defaultBuildGradle);
            FileUtils.write(defaultBuildGradle, FileUtils.readFileToString(buildGradleFile, BuilderConfig.DEFAULT_CHARSET_NAME), BuilderConfig.DEFAULT_CHARSET_NAME, false);
        }
        //创建渠道信息文件夹，自动添加到.git忽略
        File channelInfoFile = new File(projectPath + "/ChannelInfo");
        if (!channelInfoFile.exists()) {
            FileUtils.forceMkdir(channelInfoFile);
        }
        addGitignore();
    }

    private void addGitignore() {
        File gitIgnore = new File(projectPath + "/.gitignore");
        String gitIgnoreStr = "\n\n\n#builder.sh-plugin \n" +
                "/ChannelInfo";
        if (gitIgnore.exists() && !checkGitignore()) {
            try {
                FileUtils.write(gitIgnore, gitIgnoreStr, BuilderConfig.DEFAULT_CHARSET_NAME, true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private Boolean checkGitignore() {
        boolean exists = false;
        try {
            exists = FileUtils.readFileToString(new File(projectPath + "/.gitignore"), BuilderConfig.DEFAULT_CHARSET_NAME).contains("/ChannelInfo");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return exists;
    }

    @TaskAction
    public void task() throws IOException {
        init();
    }

    public Config getNewConfig() {
        Config config = new Config();
        config.setChannelFilePath("/ChannelInfo");
        config.setChannelListFile("/MkPackageName.txt");
        config.setDefaultGradleFile("/BuilderConfig/default.build.gradle");
        config.setOutputFile("/apk");
        config.setChannelJsonData("/ChannelInfo");
        List<Files> files = new ArrayList<>();
        files.add(new Files("/ic_launcher.png", "/res/mipmap-xxhdpi", "logo"));
        files.add(new Files("/loading.png", "/res/mipmap-xxhdpi", "splash"));
        config.setFilesList(files);
        return config;
    }


    public String getPrettyJson(Config config) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        return gson.toJson(config);
    }
}
