package com.builder.plugin.base

import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction

open abstract class BaseTask : DefaultTask() {

    @TaskAction
    open abstract fun action()

}