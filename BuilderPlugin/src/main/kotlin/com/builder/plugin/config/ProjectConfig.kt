package com.builder.plugin.config

import com.builder.plugin.ext.toBean
import org.apache.commons.io.FileUtils
import java.io.File


fun emptyProjectConfig(): ProjectConfig {
    return ProjectConfig().also { it.channelFiles.add(ChannelFile()) }
}

fun getProjectConfig(file: File): ProjectConfig {
    return FileUtils.readFileToString(file, BuilderConfig.CHARESET).toBean<ProjectConfig>()
}

data class ProjectConfig(
    var name: String = "",
    var version: String = "",
    var versionCode: String = "",
    var channelFilePath: String = "",
    var channelFiles: ArrayList<ChannelFile> = arrayListOf<ChannelFile>()
)

/**
 *  "projectFile": "/app/src/main/res/mipmap-xxhdpi/ic_launcher.png",
 *  "channelFile": "/ic_launcher.png"
 */
data class ChannelFile(
    /**
     * 需要保存的渠道文件,相对路径，从Project根目录到文件
     */
    var projectFile: String = "",
    /**
     * 保存到渠道文件夹的文件,相对路径.从Channel内开始
     */
    var channelFile: String = ""
)


