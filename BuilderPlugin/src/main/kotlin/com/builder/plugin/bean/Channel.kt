package com.builder.plugin.bean

import com.builder.plugin.config.BuilderConfig
import com.builder.plugin.config.ProjectConfig
import com.builder.plugin.ext.toBean
import com.builder.plugin.ext.toJson
import org.apache.commons.io.FileUtils
import java.io.File


fun getChannelByFile(file: File): Channel {
    return FileUtils.readFileToString(file, BuilderConfig.CHARESET).toBean<Channel>()
}

data class Channel(
    var name: String? = "",
    var showName: String = "",
    var applicationId: String? = "",
    var buildConfigFields: ArrayList<BuildConfigField?>? = null,
    var resValues: ArrayList<ResValue?>? = null,
    var manifestPlaceholders: ArrayList<ManifestPlaceholder?>? = null,
) {
    fun save(file: File) {
        FileUtils.write(file, this.toJson(), BuilderConfig.CHARESET)
    }
}


data class BuildConfigField(
    var type: String = "",
    var key: String = "",
    var value: String = ""
)


data class ResValue(
    var type: String = "",
    var key: String = "",
    var value: String = ""
)

data class ManifestPlaceholder(
    var key: String = "",
    var value: String = ""
)
