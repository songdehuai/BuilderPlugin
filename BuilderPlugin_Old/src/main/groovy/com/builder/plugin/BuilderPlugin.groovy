package com.builder.plugin

import com.builder.task.BuildChannelFile

import com.builder.task.ExportFlavorToFile
import com.builder.task.BuildAllChannelNoReinforce
import com.builder.task.ConfigInitTask
import org.gradle.api.Plugin
import org.gradle.api.Project

class BuilderPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.getTasks().create("ConfigInit", ConfigInitTask.class)
        project.getTasks().create("BuildAllChannel", BuildChannelFile.class)
        project.getTasks().create("BuildAllChannelNoReinforce", BuildAllChannelNoReinforce.class)
        project.getTasks().create("ExportChannelInfo", ExportFlavorToFile.class)
    }
}