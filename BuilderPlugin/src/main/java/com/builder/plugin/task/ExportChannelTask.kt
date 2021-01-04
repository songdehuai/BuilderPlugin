package com.builder.plugin.task

import com.android.build.gradle.AppExtension
import com.builder.plugin.base.BaseTask
import com.builder.plugin.bean.Channel
import com.builder.plugin.bean.ChannelFile
import com.builder.plugin.ext.getBuildConfigFields
import com.builder.plugin.ext.getManifestPlaceholders
import com.builder.plugin.ext.getResValues
import com.builder.plugin.ext.toJson

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
            channel.save()
        }

    }

}

