package com.builder.plugin.task

import com.android.build.gradle.AppExtension
import com.builder.plugin.base.BaseTask
import com.builder.plugin.bean.Channel
import com.builder.plugin.config.BuilderConfig
import com.builder.plugin.config.getProjectConfig
import com.builder.plugin.ext.*
import org.apache.commons.io.FileUtils
import org.gradle.api.Project
import org.gradle.api.invocation.Gradle

open class ExportChannelTask : BaseTask() {


    override fun action() {
        val android = project.extensions.getByType(AppExtension::class.java)
        android.productFlavors.forEach {
            "渠道:${it.name} Start".printE()
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
            val channelConfigFile = BuilderConfig.getChannelFileByChannelName(project.rootDir, it.name)
            if (!channelConfigFile.exists()) {
                channel.save(channelConfigFile)
            } else {
                "${it.name} ${channelConfigFile.name} 已经存在,跳过".logE()
            }
            //复制设置好的资源文件
            val projectConfig = getProjectConfig(BuilderConfig.getConfigFile(project.rootDir))
            projectConfig.channelFiles.forEach { channelFile ->
                val projectFile = BuilderConfig.getProjectFileByPath(project.rootDir, channelFile.projectFile)
                val channelFile = BuilderConfig.getChannelFileByPath(project.rootDir, it.name, channelFile.channelFile)
                if (!channelFile.exists()) {
                    FileUtils.copyFile(projectFile, channelFile)
                } else {
                    "${it.name} ${channelFile.name} 已经存在,跳过".logE()
                }
            }
            "渠道:${it.name} End".printE()
            emptyLine()
        }

    }

}

