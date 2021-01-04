package com.builder.plugin

import com.builder.plugin.config.BuilderConfig
import com.builder.plugin.task.ConfigInitTask
import com.builder.plugin.task.ExportChannelTask
import com.builder.plugin.task.InjectTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File

class BuilderPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val builderFile = project.rootDir.path.plus("/.builder")
        project.tasks.create("InjectTask", InjectTask::class.java).group = "builder"
        project.tasks.create("ConfigInitTask", ConfigInitTask::class.java).group = "builder"
        project.tasks.create("ExportChannelTask", ExportChannelTask::class.java).group = "builder"

        BuilderConfig.channelFilePath = File(builderFile.plus("/channels"))

    }

}