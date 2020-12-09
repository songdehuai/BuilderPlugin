package com.builder;

import com.builder.config.BuilderConfig;
import com.builder.utils.Log;
import com.builder.utils.ProcessUtils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * 编译工具类
 *
 * @author songdehuai
 */
public class BuilderTools {

    private static BuilderTools builderTools = new BuilderTools();

    private BuilderTools() {

    }

    public static BuilderTools getInstance() {
        return builderTools;
    }

    public synchronized void buildApk(String projectPath, String flavorName) {
        buildApk(projectPath, flavorName, false, false);
    }

    public synchronized void buildApkWithClean(String projectPath, String flavorName) {
        buildApk(projectPath, flavorName, false, true);
    }

    public synchronized void buildApkRelease(String projectPath, String flavorName) {
        buildApk(projectPath, flavorName, true, true);
    }

    public synchronized void buildApk(String projectPath, String flavorName, Boolean isRelease, boolean isClean) {
        String buildType = "Release";
        String cleanFlag = "clean";
        if (isRelease) {
            buildType = "Release";
        } else {
            buildType = "Debug";
        }
        if (isClean) {
            cleanFlag = "clean";
        } else {
            cleanFlag = "";
        }
        String shFileName = BuilderConfig.TEMP_FILE + "builder.sh.sh";
        String flavorLastStr = flavorName.substring(0, 1).toUpperCase();
        String name = flavorName.replaceFirst(flavorLastStr.toLowerCase(), flavorLastStr);
        File shFile = new File(shFileName);
        String shStr = "cd " + projectPath + " && ./gradlew " + cleanFlag + " assemble" + name + buildType + " -q --warning-mode all";
        try {
            FileUtils.writeStringToFile(shFile, shStr, BuilderConfig.DEFAULT_CHARSET_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ProcessUtils.Result r = ProcessUtils.run("sh " + shFileName);
        if (r.code == 0) {
            Log.log("***" + name + "***  打包完成");
        } else {
            Log.log("***" + name + "***  打包失败");
        }
    }


    public synchronized void reinforceBy360(String projectPath, String apkFile) {
        Log.log("开始加固");
        String shFileName = BuilderConfig.TEMP_FILE + "reinforceBy360.sh";
        File shFile = new File(shFileName);
        String shStr = "cd " + projectPath + "/builderx && sh builderx.sh " + apkFile + " ../ApkOutPut";
        try {
            FileUtils.writeStringToFile(shFile, shStr, BuilderConfig.DEFAULT_CHARSET_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ProcessUtils.Result r = ProcessUtils.run("sh " + shFileName);
        if (r.code == 0) {
            Log.log("加固完成");
        } else {
            Log.log("加固失败");
        }
    }


}
