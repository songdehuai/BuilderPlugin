package com.builder.plugin.task

import com.android.build.gradle.AppExtension
import com.android.builder.internal.ClassFieldImpl
import com.builder.plugin.base.BaseTask
import com.builder.plugin.bean.getChannelByFile
import com.builder.plugin.bean.getTasks
import com.builder.plugin.config.BuilderConfig
import com.builder.plugin.config.getProjectConfig
import com.builder.plugin.ext.*

open class InjectTask : BaseTask() {

    override fun action() {

        val android = project.extensions.getByType(AppExtension::class.java)
        val projectConfig = getProjectConfig(BuilderConfig.getConfigFile(project.rootDir))
        getTasks(project.rootDir).forEach {
            val channelFile = BuilderConfig.getChannelFileByChannelName(project.rootDir, it)
            val channel = getChannelByFile(channelFile)
            //注入渠道
            android.productFlavors.create(channel.name) { productFlavor ->
                productFlavor.applicationId = channel.applicationId
                productFlavor.addManifestPlaceholders(channel.manifestPlaceholders.getManifestPlaceholders())
                productFlavor.addBuildConfigFields(channel.buildConfigFields.getBuildConfigFields())
                productFlavor.addResValues(channel.resValues.getResValues())
            }
        }


        android.productFlavors.forEach {
            println("渠道信息${it.name}")
        }


    }
}



