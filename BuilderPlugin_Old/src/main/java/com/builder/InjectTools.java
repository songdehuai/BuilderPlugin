package com.builder;

import com.builder.config.BuilderConfig;
import com.builder.entity.Flavor;
import com.builder.utils.Log;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 渠道信息注入工具类
 *
 * @author songdehuai
 */
public class InjectTools {

    private static InjectTools injectTools = new InjectTools();

    private InjectTools() {

    }

    public static InjectTools getInstance() {
        return injectTools;
    }

    /**
     * 获取渠道文件行号，用于注入渠道信息
     *
     * @param gradleFile gradle文件绝对路径
     * @return result[0] 为'-1'时，即不存在渠道信息，行号从result[1] 获取
     * result[0] 不为'-1'时，即已经存在渠道信息，行号从result[0] 取
     */
    public int[] getFlavorsLine(String gradleFile) {
        List<String> configArray = new ArrayList<>();
        int[] result = new int[]{0, -1};
        File file = new File(gradleFile);
        if (!file.exists()) {
            Log.log(gradleFile + " 不存在");
            System.exit(0);
        }
        int startLine = -1;
        int androidLines = -1;
        try {
            configArray = FileUtils.readLines(file, BuilderConfig.DEFAULT_CHARSET_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String temp = "";
        String flavorsTemp = "";
        String androidTemp = "";
        for (int i = 0; i < configArray.size(); i++) {
            temp = configArray.get(i);
            temp = temp.replace(" ", "");
            if (temp.contains("android{")) {
                androidLines = i;
            }
            if (temp.contains("android")) {
                androidLines = i;
                for (int k = 0; k < configArray.size(); k++) {
                    androidTemp = configArray.get(k);
                    if (androidTemp.contains("{")) {
                        androidLines = k;
                        break;
                    }
                }
            }
            if (temp.contains("productFlavors{")) {
                startLine = i;
                break;
            }
            if (temp.contains("productFlavors")) {
                startLine = i;
                for (int j = startLine; j < configArray.size(); j++) {
                    flavorsTemp = configArray.get(j);
                    if (flavorsTemp.contains("{")) {
                        startLine = j;
                        break;
                    }
                }
                break;
            }

        }
        result[0] = startLine;
        result[1] = androidLines;
        return result;
    }

    /**
     * 从
     * {@link com.builder.config.BuilderConfig }:DEFAULT_BUILD_GRADLE
     * 复制文件并且注入Flavor到
     * {@link com.builder.config.BuilderConfig }:GRADLE_FILE_NAME
     *
     * @param projectPath Project绝对路径
     * @param flavor      渠道信息数据
     * @param line        注入位置
     */
    public synchronized boolean inject(String projectPath, String flavor, int line) {
        //移动原gralde文件
        String gradlePath = projectPath + "/app";
        String defaultGradlePath = BuilderConfig.getInstance().DEFAULT_BUILD_GRADLE();
        File file = new File(defaultGradlePath);
        if (!file.exists()) {
            Log.log(defaultGradlePath + " 不存在!");
            return false;
        }

        gradlePath += BuilderConfig.GRADLE_FILE_NAME;
        List<String> configArray = new ArrayList<>();
        try {
            configArray = FileUtils.readLines(file, BuilderConfig.DEFAULT_CHARSET_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //插入代码
        File newFile = new File(gradlePath);
        if (newFile.exists()) {
            try {
                //复制文件到.idea
                FileUtils.copyFileToDirectory(newFile, new File(BuilderConfig.TEMP_FILE), true);
            } catch (IOException e) {
                e.printStackTrace();
            }
            newFile.delete();
            try {
                newFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        StringBuffer strBuffer = new StringBuffer();
        for (int i = 0; i < configArray.size(); i++) {
            if (i == line) {
                strBuffer.append(configArray.get(i)).append("\n").append(flavor).append("\n");
            } else {
                strBuffer.append(configArray.get(i)).append("\n");
            }
        }
        try {
            FileUtils.writeStringToFile(newFile, strBuffer.toString(), BuilderConfig.DEFAULT_CHARSET_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 从
     * {@link com.builder.config.BuilderConfig }:DEFAULT_BUILD_GRADLE
     * 复制文件并且注入Flavor到
     * {@link com.builder.config.BuilderConfig }:GRADLE_FILE_NAME
     *
     * @param projectPath Project绝对路径
     * @param flavor      渠道信息对象
     */
    public synchronized boolean inject(String projectPath, Flavor flavor) {
        String defaultFilePath = BuilderConfig.getInstance().DEFAULT_BUILD_GRADLE();
        String appPath = projectPath + "/app";
        int[] startLine = getFlavorsLine(defaultFilePath);
        int line = startLine[0];
        boolean isExist = startLine[0] != -1;
        if (!isExist) {
            line = startLine[1];
        }
        return inject(projectPath, FlavorTools.getInstance().genFlavors(flavor, appPath, isExist), line);
    }

    /**
     * 从
     *
     * @param flavorFilePath 复制文件并且注入Flavor到
     *                       {@link com.builder.config.BuilderConfig }:GRADLE_FILE_NAME
     * @param projectPath    ProjectModule绝对路径
     * @param flavorFilePath 渠道信息文件路径
     */
    public synchronized boolean inject(String projectPath, String flavorFilePath) throws IOException {
        File file = new File(flavorFilePath);
        if (!file.exists()) {
            Log.i(flavorFilePath + " 不存在,跳过");
            return false;
        }
        String json = FileUtils.readFileToString(new File(flavorFilePath), BuilderConfig.DEFAULT_CHARSET_NAME);
        return inject(projectPath, Flavor.toFlavor(json));
    }

    /**
     * 从
     *
     * @param flavorFile  复制文件并且注入Flavor到
     *                    {@link com.builder.config.BuilderConfig }:GRADLE_FILE_NAME
     * @param projectPath Project绝对路径
     * @param flavorFile  渠道信息文件路径
     */
    public synchronized boolean inject(String projectPath, File flavorFile) throws IOException {
        String json = FileUtils.readFileToString(flavorFile, BuilderConfig.DEFAULT_CHARSET_NAME);
        return inject(projectPath, Flavor.toFlavor(json));
    }
}
