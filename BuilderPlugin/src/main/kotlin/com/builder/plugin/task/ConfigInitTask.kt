package com.builder.plugin.task

import com.builder.plugin.base.BaseTask
import com.builder.plugin.config.BuilderConfig
import com.builder.plugin.config.ProjectConfig
import com.builder.plugin.ext.log
import com.builder.plugin.ext.toJson
import org.apache.commons.io.FileUtils
import java.io.File

open class ConfigInitTask : BaseTask() {

    private val builderFile by lazy { File(project.rootDir.path.plus("/.builder")) }

    private val projectConfig by lazy { File(project.rootDir.path.plus("/.builder/config.json")) }

    override fun action() {
        "项目路径:${project.buildFile.absolutePath}".log()
        checkConfigFolder()
        checkProjectConfig()
    }

    private fun checkProjectConfig() {
        if (!projectConfig.exists()) {
            projectConfig.createNewFile()
            FileUtils.writeStringToFile(projectConfig, ProjectConfig().toJson(), BuilderConfig.CHARESET)
        }
    }

    private fun checkConfigFolder() {
        if (!builderFile.exists()) {
            builderFile.mkdir()
        }
    }
}