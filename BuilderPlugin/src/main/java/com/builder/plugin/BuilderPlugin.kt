package com.builder.plugin

import com.builder.plugin.task.ConfigTask
import com.builder.plugin.task.InjectTask
import com.builder.plugin.task.Up2Server
import org.gradle.api.Plugin
import org.gradle.api.Project

class BuilderPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.tasks.create("InjectTask", InjectTask::class.java).group = "builder"
        project.tasks.create("ConfigTask", ConfigTask::class.java).group = "builder"
        project.tasks.create("Up2Server", Up2Server::class.java).group = "builder"
    }

}