package com.builder.plugin.task

import com.android.build.gradle.AppExtension
import com.builder.plugin.base.BaseTask
import com.builder.plugin.config.BuilderConfig
import com.builder.plugin.config.emptyProjectConfig
import com.builder.plugin.ext.log
import com.builder.plugin.ext.toJson
import org.apache.commons.io.FileUtils
import java.io.File

open class ConfigInitTask : BaseTask() {

    private val builderFile by lazy { BuilderConfig.getBuilderFile(project.rootDir) }

    private val projectConfigFile by lazy { BuilderConfig.getConfigFile(project.rootDir) }

    private val taskFile by lazy { BuilderConfig.getTaskFile(project.rootDir) }

    override fun action() {
        "项目路径:${project.rootDir}".log()
        checkConfigFolder()
        checkProjectConfig()
        checkTaskFile()
    }

    private fun checkTaskFile() {
        if (!taskFile.exists()) {
            taskFile.createNewFile()
        }
    }

    private fun checkProjectConfig() {
        if (!projectConfigFile.exists()) {
            projectConfigFile.createNewFile()
            FileUtils.writeStringToFile(projectConfigFile, emptyProjectConfig().toJson(), BuilderConfig.CHARESET)
        }
    }

    private fun checkConfigFolder() {
        if (!builderFile.exists()) {
            builderFile.mkdir()
        }
    }
}