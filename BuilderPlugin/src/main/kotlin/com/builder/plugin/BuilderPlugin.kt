package com.builder.plugin

import com.builder.plugin.config.BuilderConfig
import com.builder.plugin.task.ConfigInitTask
import com.builder.plugin.task.ExportChannelTask
import com.builder.plugin.task.ForceExportChannelTask
import com.builder.plugin.task.InjectTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File

class BuilderPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.tasks.create("InjectTask", InjectTask::class.java).group = "builder"
        project.tasks.create("ConfigInitTask", ConfigInitTask::class.java).group = "builder"
        project.tasks.create("ExportChannelTask", ExportChannelTask::class.java).group = "builder"
        project.tasks.create("ForceExportChannelTask", ForceExportChannelTask::class.java).group = "builder"

    }

}