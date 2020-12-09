package com.builder.plugin.bean

import com.builder.plugin.config.BuilderConfig
import com.builder.plugin.ext.toJson
import org.apache.commons.io.FileUtils
import java.io.File

data class Channel(
    var name: String? = "",
    var showName: String = "",
    var applicationId: String? = "",
    var buildConfigFields: ArrayList<BuildConfigField?>? = null,
    var resValues: ArrayList<ResValue?>? = null,
    var manifestPlaceholders: ArrayList<ManifestPlaceholder?>? = null,
    var folder: File? = null,
    var files: List<ChannelFile?>? = null
) {
    fun save() {
        val file =
            File(BuilderConfig.channelFilePath.absolutePath.plus("/${name}").plus("/config.json"))
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


data class ChannelFile(
    var injectFileName: String,
    var injectFilePath: String,
    var injectFile: File? = null,
    var outFileName: String,
    var outFilePath: String,
    var outFile: File? = null
)