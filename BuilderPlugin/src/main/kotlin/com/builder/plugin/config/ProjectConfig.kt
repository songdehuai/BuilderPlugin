package com.builder.plugin.config


data class ProjectConfig(
    var name: String = "",
    var version: String = "",
    var versionCode: String = "",
    var channelFiles: ArrayList<ChannelFile> = arrayListOf<ChannelFile>()
) {

}

data class ChannelFile(var projectFile: String = "")


