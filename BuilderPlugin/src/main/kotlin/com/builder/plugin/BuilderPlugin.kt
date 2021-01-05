package com.builder.plugin

import com.builder.plugin.bean.getTasks
import com.builder.plugin.config.BuilderConfig
import com.builder.plugin.ext.printE
import com.builder.plugin.task.ConfigInitTask
import com.builder.plugin.task.ExportChannelTask
import com.builder.plugin.task.ForceExportChannelTask
import com.builder.plugin.task.InjectTask
import org.gradle.BuildListener
import org.gradle.BuildResult
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.initialization.Settings
import org.gradle.api.invocation.Gradle
import java.io.File

class BuilderPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.tasks.create("InjectTask", InjectTask::class.java).group = "builder"
        project.tasks.create("ConfigInitTask", ConfigInitTask::class.java).group = "builder"
        project.tasks.create("ExportChannelTask", ExportChannelTask::class.java).group = "builder"
        project.tasks.create("ForceExportChannelTask", ForceExportChannelTask::class.java).group = "builder"


        project.gradle.addBuildListener(object : BuildListener {
            override fun buildStarted(gradle: Gradle) {
                "buildStarted".printE()
            }

            override fun settingsEvaluated(settings: Settings) {
                "settingsEvaluated".printE()
            }

            override fun projectsLoaded(gradle: Gradle) {
                "projectsLoaded".printE()
            }

            override fun projectsEvaluated(gradle: Gradle) {
                "projectsEvaluated".printE()
            }

            override fun buildFinished(result: BuildResult) {
                "buildFinished".printE()
            }

        })
    }

}