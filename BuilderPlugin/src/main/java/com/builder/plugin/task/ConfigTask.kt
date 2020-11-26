package com.builder.plugin.task

import com.android.build.gradle.internal.ide.dependencies.computeBuildMapping
import com.builder.plugin.base.BaseTask
import java.io.File

open class ConfigTask : BaseTask() {

    override fun action() {
        val builderFile = File(project.rootDir.path.plus("/.builder"))
        println("AllProjects:${project.allprojects}")
        println("${project.name}")
        println(project.buildFile.absolutePath)
        if (!builderFile.exists()) {
            builderFile.mkdir()
        }
    }
}