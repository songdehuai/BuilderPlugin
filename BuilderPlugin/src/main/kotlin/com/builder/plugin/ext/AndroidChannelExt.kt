package com.builder.plugin.ext

import com.android.builder.internal.ClassFieldImpl
import com.android.builder.model.ClassField
import com.builder.plugin.bean.BuildConfigField
import com.builder.plugin.bean.ManifestPlaceholder
import com.builder.plugin.bean.ResValue


fun Map<String, ClassField>?.getBuildConfigFields(): ArrayList<BuildConfigField> {
    val buildConfigFields = arrayListOf<BuildConfigField>()
    this?.forEach {
        buildConfigFields.add(BuildConfigField(it.value.type, it.value.name, it.value.value))
    }
    return buildConfigFields
}

fun Map<String, ClassField>?.getResValues(): ArrayList<ResValue> {
    val buildConfigFields = arrayListOf<ResValue>()
    this?.forEach {
        buildConfigFields.add(ResValue(it.value.type, it.value.name, it.value.value))
    }
    return buildConfigFields
}

fun Map<String, Any>?.getManifestPlaceholders(): ArrayList<ManifestPlaceholder> {
    val buildConfigFields = arrayListOf<ManifestPlaceholder>()
    this?.forEach {
        buildConfigFields.add(ManifestPlaceholder(it.key, it.value.toString()))
    }
    return buildConfigFields
}

fun ArrayList<BuildConfigField?>?.getBuildConfigFields(): Map<String, ClassField> {
    val buildConfigFields = mutableMapOf<String, ClassField>()
    this?.forEach {
        it?.let {
            buildConfigFields[it.key] = ClassFieldImpl(it.type, it.key, it.value)
        }
    }
    return buildConfigFields
}

fun ArrayList<ResValue?>?.getResValues(): Map<String, ClassField> {
    val buildConfigFields = mutableMapOf<String, ClassField>()
    this?.forEach {
        it?.let {
            buildConfigFields[it.key] = ClassFieldImpl(it.type, it.key, it.value)
        }
    }
    return buildConfigFields
}

fun ArrayList<ManifestPlaceholder?>?.getManifestPlaceholders(): Map<String, Any> {
    val buildConfigFields = mutableMapOf<String, Any>()
    this?.forEach {
        it?.let {
            buildConfigFields[it.key] = it.value
        }
    }
    return buildConfigFields
}
