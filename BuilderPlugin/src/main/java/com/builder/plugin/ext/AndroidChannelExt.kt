package com.builder.plugin.ext

import com.android.builder.model.ClassField
import com.builder.plugin.bean.BuildConfigField
import com.builder.plugin.bean.ManifestPlaceholder
import com.builder.plugin.bean.ResValue


fun Map<String, ClassField>.getBuildConfigFields(): ArrayList<BuildConfigField> {
    val buildConfigFields = arrayListOf<BuildConfigField>()
    this.forEach {
        buildConfigFields.add(BuildConfigField(it.value.type, it.value.name, it.value.value))
    }
    return buildConfigFields
}


fun Map<String, ClassField>.getResValues(): ArrayList<ResValue> {
    val buildConfigFields = arrayListOf<ResValue>()
    this.forEach {
        buildConfigFields.add(ResValue(it.value.type, it.value.name, it.value.value))
    }
    return buildConfigFields
}


fun Map<String, Any>.getManifestPlaceholders(): ArrayList<ManifestPlaceholder> {
    val buildConfigFields = arrayListOf<ManifestPlaceholder>()
    this.forEach {
        buildConfigFields.add(ManifestPlaceholder(it.key, it.value.toString()))
    }
    return buildConfigFields
}
