package com.builder.plugin.task

import com.android.build.api.variant.BuildConfigField
import com.android.build.gradle.AppExtension
import com.android.builder.internal.BaseConfigImpl
import com.android.builder.internal.ClassFieldImpl
import com.android.builder.model.ClassField
import com.builder.plugin.base.BaseTask

open class InjectTask : BaseTask() {

    override fun action() {
        val android = project.extensions.getByType(AppExtension::class.java)

        val field = ClassFieldImpl("String", "BASE_URL", "hhhhhhhh")

        android.productFlavors.create("nihao3") {
            it.addManifestPlaceholders(mapOf(Pair("geo_key", "dsadadasd")))
            it.applicationId = "com.test.11"
            it.addBuildConfigField(field)
        }

        android.productFlavors.forEach {
            println("渠道信息${it.name}")
            it.buildConfigFields.forEach {
                println("渠道详细信息${it.key}${it.value.value}")
            }
        }
    }
}

