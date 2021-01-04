package com.builder.plugin.task

import com.android.build.gradle.AppExtension
import com.builder.plugin.base.BaseTask
import com.builder.plugin.bean.Channel
import com.builder.plugin.config.BuilderConfig
import com.builder.plugin.config.getProjectConfig
import com.builder.plugin.ext.*
import org.apache.commons.io.FileUtils

open class ExportChannelTask : BaseTask() {


    override fun action() {
        val android = project.extensions.getByType(AppExtension::class.java)
        android.productFlavors.forEach {
            val channel = Channel()
            channel.applicationId = it.applicationId
            channel.name = it.name
            channel.buildConfigFields = arrayListOf()
            channel.buildConfigFields?.addAll(it.buildConfigFields.getBuildConfigFields())
            channel.resValues = arrayListOf()
            channel.resValues?.addAll(it.resValues.getResValues())
            channel.manifestPlaceholders = arrayListOf()
            channel.manifestPlaceholders?.addAll(it.manifestPlaceholders.getManifestPlaceholders())
            //解析渠道信息,转换成Json,保存到渠道指定的文件夹
            channel.save(BuilderConfig.getChannelFileByChannelName(project.rootDir, it.name))
            //复制设置好的资源文件
            val projectConfig = getProjectConfig(BuilderConfig.getConfigFile(project.rootDir))
            projectConfig.channelFiles.forEach { channelFile ->
                FileUtils.copyFile(BuilderConfig.getProjectFileByPath(project.rootDir, channelFile.projectFile), BuilderConfig.getChannelFileByPath(project.rootDir, it.name, channelFile.channelFile))
            }
        }

    }

}

