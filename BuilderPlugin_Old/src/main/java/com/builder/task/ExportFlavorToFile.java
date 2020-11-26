package com.builder.task;

import com.android.build.gradle.AppExtension;
import com.android.build.gradle.internal.dsl.ProductFlavor;
import com.android.builder.model.ClassField;
import com.builder.config.BuilderConfig;
import com.builder.entity.BuildConfigField;
import com.builder.entity.Files;
import com.builder.entity.Flavor;
import com.builder.entity.ManifestPlaceholder;
import com.builder.entity.ResValue;
import com.builder.utils.Log;
import com.google.gson.Gson;

import org.apache.commons.io.FileUtils;
import org.gradle.api.DefaultTask;
import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExportFlavorToFile extends DefaultTask {

    public ExportFlavorToFile() {
        setGroup(BuilderConfig.GROUP_NAME);
    }

    @TaskAction
    public void task() {
        String path = getProject().getParent().getProjectDir().toString();
        BuilderConfig.getInstance().setConfig(path);
        AppExtension android = getProject().getExtensions().getByType(AppExtension.class);
        getFlavors(android, path);

    }

    List<Flavor> getFlavors(AppExtension android, String projectFile) {
        List<Flavor> flavorList = new ArrayList<>();
        NamedDomainObjectContainer<ProductFlavor> flavors = android.getProductFlavors();
        for (ProductFlavor flavor : flavors) {
            Log.i("复制" + flavor.getName());
            Flavor mFlavor = new Flavor();
            mFlavor.setName(flavor.getName());
            mFlavor.setApplicationId(flavor.getApplicationId());
            List<BuildConfigField> buildConfigFields = new ArrayList<>();
            Map<String, ClassField> fieldMap = flavor.getBuildConfigFields();
            for (Map.Entry<String, ClassField> entry : fieldMap.entrySet()) {
                String mapKey = entry.getKey();
                ClassField mapValue = entry.getValue();
                BuildConfigField buildConfigField = new BuildConfigField(mapValue.getType(), mapKey, mapValue.getValue());
                buildConfigFields.add(buildConfigField);
            }
            mFlavor.setBuildConfigFieldList(buildConfigFields);

            List<ResValue> resValueList = new ArrayList<>();
            Map<String, ClassField> res = flavor.getResValues();
            for (Map.Entry<String, ClassField> entry : res.entrySet()) {
                String mapKey = entry.getKey();
                ClassField mapValue = entry.getValue();
                ResValue resValue = new ResValue(mapValue.getType(), mapKey, mapValue.getValue());
                resValueList.add(resValue);
                System.out.println(mapKey + ":" + mapValue.getValue());
            }
            mFlavor.setResValueList(resValueList);

            List<ManifestPlaceholder> manifestPlaceholderList = new ArrayList<>();
            Map<String, Object> manifest = flavor.getManifestPlaceholders();
            for (Map.Entry<String, Object> entry : manifest.entrySet()) {
                String mapKey = entry.getKey();
                Object mapValue = entry.getValue();
                ManifestPlaceholder manifestPlaceholder = new ManifestPlaceholder();
                manifestPlaceholder.setKey(mapKey);
                manifestPlaceholder.setValue(mapValue.toString());
                manifestPlaceholderList.add(manifestPlaceholder);
                System.out.println(mapKey + ":" + mapValue.toString());
            }
            mFlavor.setManifestPlaceholders(manifestPlaceholderList);

            flavorList.add(mFlavor);

            Gson gson = new Gson();
            String temp = gson.toJson(mFlavor);
            String outPutFilePath = BuilderConfig.getInstance().CHANNEL_DATA_PATH() + "/" + mFlavor.getName() + "/";
            try {

                File fileTemp = new File(outPutFilePath + "info.json");
                Log.log(fileTemp.getAbsolutePath());
                FileUtils.writeStringToFile(fileTemp, temp, BuilderConfig.DEFAULT_CHARSET_NAME);
                mvFlavor(projectFile, outPutFilePath, mFlavor.getName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.i("复制完成");
        return flavorList;
    }


    public void mvFlavor(String projectFile, String outPutFilePath, String flavorName) throws IOException {
        List<Files> filesList = BuilderConfig.getInstance().FILES();
        if (filesList != null) {
            for (Files files : filesList) {
                String temp = files.getFromFile();
                File file = new File(projectFile + "/app/src/" + flavorName + files.getToFile() + "/" + files.getFromFile());
                Log.log(file.getAbsolutePath());
                if (file.exists()) {
                    FileUtils.copyFileToDirectory(file, new File(outPutFilePath));
                    Log.log(file.getAbsolutePath());
                } else {
                    Log.log(flavorName + files.getToFile() + files.getFromFile() + ":不存在");
                }
            }
        }
    }

}
