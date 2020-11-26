package com.builder;

import com.builder.config.BuilderConfig;
import com.builder.entity.BuildConfigField;
import com.builder.entity.Files;
import com.builder.entity.Flavor;
import com.builder.entity.ManifestPlaceholder;
import com.builder.entity.ResValue;
import com.builder.utils.Log;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 渠道信息工具
 *
 * @author songdehuai
 */
public class FlavorTools {

    private static FlavorTools flavorBuilderTools = new FlavorTools();

    private FlavorTools() {

    }

    public static FlavorTools getInstance() {
        return flavorBuilderTools;
    }

    private String BUILDCONFIGFIELD = "buildConfigField";

    private String RESVALUE = "resValue";

    /**
     * 生成buildConfigField
     *
     * @param buildConfigField 编译配置信息
     * @return 解析成String格式的数据
     */
    private String genBuildConfigField(BuildConfigField buildConfigField) {
        String buildConfigFieldStr = "";
        String typeTemp = buildConfigField.getType().toLowerCase();
        switch (typeTemp) {
            case "string":
                buildConfigFieldStr = BUILDCONFIGFIELD
                        + " \""
                        + buildConfigField.getType()
                        + "\", \""
                        + buildConfigField.getKey()
                        + "\", '\""
                        + buildConfigField.getValue() +
                        "\"' ";
                break;
            case "boolean":
                buildConfigFieldStr = BUILDCONFIGFIELD
                        + " \""
                        + buildConfigField.getType()
                        + "\", \""
                        + buildConfigField.getKey()
                        + "\", '"
                        + buildConfigField.getValue() +
                        "' ";
                break;
        }
        return buildConfigFieldStr;
    }

    /**
     * 生成ResValue
     *
     * @param resValue 资源信息
     * @return 解析成String格式的数据
     */
    private String genResValue(ResValue resValue) {
        String resValueStr = "";
        resValueStr = RESVALUE
                + " \""
                + resValue.getType()
                + "\", \""
                + resValue.getKey()
                + "\", '\""
                + resValue.getValue() +
                "\"' ";
        return resValueStr;
    }

    /**
     * 生成 manifestPlaceholders
     *
     * @param manifestPlaceholders
     * @return
     */
    private String genManifestPlaceholders(List<ManifestPlaceholder> manifestPlaceholders) {
        String result = "";
        if (manifestPlaceholders != null && manifestPlaceholders.size() > 0) {
            StringBuffer stringBuffer = new StringBuffer("");
            stringBuffer.append("manifestPlaceholders = [");
            for (int i = 0; i < manifestPlaceholders.size(); i++) {
                stringBuffer
                        .append(manifestPlaceholders.get(i).getKey())
                        .append(":")
                        .append("\"")
                        .append(manifestPlaceholders.get(i).getValue())
                        .append("\"")
                        .append(",")
                        .append("\n");
            }
            result = stringBuffer.substring(0, stringBuffer.length() - 2);
            result += "\n]";
        }
        return result;
    }

    /**
     * 生成渠道信息数据
     *
     * @param flavor  渠道信息对象
     * @param isExist 是否已经存在渠道信息
     * @return 解析成String格式的数据
     */
    public String genFlavors(Flavor flavor, String path, boolean isExist) {
        StringBuffer stringBuffer = new StringBuffer();
        //拼接头
        if (!isExist) {
            stringBuffer.append("    productFlavors { \n");
        }
        stringBuffer.append("        ").append(flavor.getName()).append(" { \n");
        //处理ApplicationId
        if (flavor.getApplicationId() != null && !flavor.getApplicationId().isEmpty()) {
            stringBuffer.append("            ").append("applicationId " + "\"").append(flavor.getApplicationId()).append("\"");
            stringBuffer.append("\n");
        }
        //处理BuildConfigField
        List<BuildConfigField> buildConfigFields = flavor.getBuildConfigFieldList();
        for (BuildConfigField field : buildConfigFields) {
            stringBuffer.append("            ").append(genBuildConfigField(field));
            stringBuffer.append("            ").append("\n");
        }
        //处理ResValue
        List<ResValue> resValues = flavor.getResValueList();
        for (ResValue resValue : resValues) {
            stringBuffer.append("            ").append(genResValue(resValue));
            stringBuffer.append("            ").append("\n");
        }
        //处理manifestPlaceholders
        List<ManifestPlaceholder> manifestPlaceholders = flavor.getManifestPlaceholders();
        stringBuffer.append("            ").append(genManifestPlaceholders(manifestPlaceholders));
        stringBuffer.append("            ").append("\n");

        stringBuffer.append("        }");
        //拼接尾
        if (!isExist) {
            stringBuffer.append("\n");
            stringBuffer.append("    }");
        }
        //复制渠道文件
        copyFlavorFiles(flavor.getName(), path, BuilderConfig.getInstance().FLAVOR_FILES());
        return stringBuffer.toString();
    }

    /**
     * 复制渠道文件到Project
     */
    public boolean copyFlavorFiles(String flavorName, String projectFilePath, String filePath) {
        filePath += "/" + flavorName;
        System.out.println("渠道文件路径:" + filePath);
        File projectFile = new File(projectFilePath + "/src/" + flavorName);
        File channleFile = new File(filePath);
        //删除原渠道信息文件
        try {
            if (projectFile.exists()) {
                System.out.println("渠道信息已经存在,删除原有文件");
                FileUtils.deleteDirectory(projectFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Files> files = BuilderConfig.getInstance().FILES();
        if (files != null) {
            for (Files file : files) {
                File fromFile = new File(channleFile + file.getFromFile());
                if (!fromFile.exists()) {
                    return false;
                }

                File toFile = new File(projectFile + file.getToFile());
                toFile.mkdirs();

                try {
                    FileUtils.copyFileToDirectory(fromFile, toFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
//        //创建文件夹
//        File mipmap_xxhdpi = new File(projectFile + BuilderConfig.getInstance().LOGO_PATH());
//        mipmap_xxhdpi.mkdirs();
//        File drawable_xxhdpi = new File(projectFile + BuilderConfig.getInstance().SPLASH_PATH());
//        drawable_xxhdpi.mkdirs();
//
//        //复制文件
//        File logo = new File(channleFile + BuilderConfig.getInstance().LOGO());
//        if (!logo.exists()) {
//            return false;
//        }
//        File load = new File(channleFile + BuilderConfig.getInstance().SPLASH());
//        if (!load.exists()) {
//            return false;
//        }
//
//        try {
//            //复制logo
//            FileUtils.copyFileToDirectory(logo, mipmap_xxhdpi);
//            //复制启动图
//            FileUtils.copyFileToDirectory(load, drawable_xxhdpi);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return true;
    }

    /**
     * 把原来的gradle移动回来
     *
     * @param projectFile
     */
    public void mvOldGradle(String projectFile) {
        File file = new File(BuilderConfig.TEMP_FILE + "build.gradle");
        File gradleFile = new File(projectFile + "/build.gradle");
        if (gradleFile.exists()) {
            gradleFile.delete();
        }
        try {
            FileUtils.copyFileToDirectory(file, new File(projectFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void delFlavorTempFiles(String projectFile, String flavorName) {
        File file = new File(projectFile + "/src/" + flavorName);
        try {
            FileUtils.deleteDirectory(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cpAPK(String projectFile, String flavorName) {
        File file = new File(projectFile + "/build/outputs/apk/" + flavorName + "/release/app-" + flavorName.toLowerCase() + "-release.apk");
        if (!file.exists()) {
            Log.e(file.getAbsolutePath() + " 不存在");
            return;
        }
        File outPutFile = new File(BuilderConfig.getInstance().OUTPUT_FILE_PATH());
        if (!outPutFile.exists()) {
            outPutFile.mkdirs();
        }
        try {
            FileUtils.copyFileToDirectory(file, outPutFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取输出后的文件名
     *
     * @param flavorName
     * @return
     */
    public String getApkName(String flavorName) {
        return BuilderConfig.getInstance().OUTPUT_FILE_PATH() + "/app-" + flavorName.toLowerCase() + "-release.apk";
    }

}
