package com.builder.task;

import com.builder.BuilderTools;
import com.builder.ChannelTools;
import com.builder.FlavorTools;
import com.builder.InjectTools;
import com.builder.config.BuilderConfig;
import com.builder.utils.Log;

import org.apache.commons.io.FileUtils;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 读取渠道文件打包
 */
public class BuildChannelFile extends DefaultTask {

    public BuildChannelFile() {
        setGroup(BuilderConfig.GROUP_NAME);
    }

    @TaskAction
    public void task() throws IOException {
        String filePath = getProject().getProjectDir().toString();
        String path = getProject().getParent().getProjectDir().toString();
        BuilderConfig.getInstance().setConfig(path);
        Log.log("开始");
        Log.ln();
        Log.ln();
        File channelFile = new File(BuilderConfig.getInstance().FLAVOR_LIST_FILE());

        File channelFileResult = new File(channelFile.getParent() + "/" + channelFile.getName().replace(".txt", "") + BuilderConfig.RESULT_FILE_NAME);

        if (channelFileResult.exists()) {
            channelFileResult.delete();
            channelFileResult.createNewFile();
        }
        if (!channelFile.exists()) {
            Log.i("渠道文件不存在!");
            System.exit(1);
        }
        StringBuffer resultStr = new StringBuffer();
        List<String> list = ChannelTools.getInstance().getChannelList(channelFile);
        String infoFilePath, logoFilePath, loadFilePath;
        File infoFile, logoFile, loadFile;
        for (String str : list) {
            Log.ln();
            Log.ln();
            infoFilePath = BuilderConfig.getInstance().FLAVOR_FILES() + "/" + str + "/info.json";
            logoFilePath = BuilderConfig.getInstance().FLAVOR_FILES() + "/" + str + "/icon_launcher.png";
            loadFilePath = BuilderConfig.getInstance().FLAVOR_FILES() + "/" + str + "/loading.png";
            infoFile = new File(infoFilePath);
            logoFile = new File(logoFilePath);
            loadFile = new File(loadFilePath);
            if (infoFile.exists() && logoFile.exists() && loadFile.exists()) {
                try {
                    Log.log(str + " 开始注入");
                    boolean result = InjectTools.getInstance().inject(path, infoFile);
                    if (result) {
                        Log.log(str + " 开始编译");
                        BuilderTools.getInstance().buildApkRelease(path, str);
                        Log.log(str + " 输出APK");
                        Log.log(FlavorTools.getInstance().getApkName(str));
                        FlavorTools.getInstance().cpAPK(filePath, str);
                        Log.log(str + " 清理文件");
                        FlavorTools.getInstance().mvOldGradle(filePath);
                        FlavorTools.getInstance().delFlavorTempFiles(filePath, str);
                        resultStr.append(str).append("      OK").append("\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Log.log(str + " 渠道信息不完整,跳过");
                resultStr.append(str).append("      Error").append("\n");
            }
            BuilderTools.getInstance().reinforceBy360(path, FlavorTools.getInstance().getApkName(str));
        }
        Log.ln();
        Log.ln();
        Log.log("结束");
        FileUtils.deleteDirectory(new File(BuilderConfig.TEMP_FILE));
        try {
            FileUtils.writeStringToFile(channelFileResult, resultStr.toString(), BuilderConfig.DEFAULT_CHARSET_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
