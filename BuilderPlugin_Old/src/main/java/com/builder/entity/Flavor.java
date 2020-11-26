package com.builder.entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class Flavor {

    private String name;
    private String applicationId;
    private List<BuildConfigField> buildConfigFieldList;
    private List<ResValue> resValueList;
    private List<ManifestPlaceholder> manifestPlaceholders;

    public Flavor() {
        buildConfigFieldList = new ArrayList<>();
        resValueList = new ArrayList<>();
        manifestPlaceholders = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public List<BuildConfigField> getBuildConfigFieldList() {
        return buildConfigFieldList;
    }

    public void setBuildConfigFieldList(List<BuildConfigField> buildConfigFieldList) {
        this.buildConfigFieldList = buildConfigFieldList;
    }

    public List<ResValue> getResValueList() {
        return resValueList;
    }

    public void setResValueList(List<ResValue> resValueList) {
        this.resValueList = resValueList;
    }


    public void addBuildConfigFields(BuildConfigField field) {
        buildConfigFieldList.add(field);
    }

    public void addResValues(ResValue resValue) {
        resValueList.add(resValue);
    }

    public List<ManifestPlaceholder> getManifestPlaceholders() {
        return manifestPlaceholders;
    }

    public void setManifestPlaceholders(List<ManifestPlaceholder> manifestPlaceholders) {
        this.manifestPlaceholders = manifestPlaceholders;
    }

    public void addManifestPlaceholders(ManifestPlaceholder manifestPlaceholder) {
        this.manifestPlaceholders.add(manifestPlaceholder);
    }

    public String getJsonStr() {
        return new Gson().toJson(this);
    }

    public static Flavor toFlavor(String json) {
        Flavor flavor = new Flavor();
        flavor = new Gson().fromJson(json, new TypeToken<Flavor>() {
        }.getType());
        return flavor;
    }
}
