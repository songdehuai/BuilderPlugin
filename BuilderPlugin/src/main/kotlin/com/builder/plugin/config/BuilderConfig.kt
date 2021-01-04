package com.builder.plugin.config

import com.builder.plugin.ext.toFile
import java.io.File

object BuilderConfig {

    const val CHARESET = "UTF-8"


    /**
     * 获得打包临时文件夹路径
     * @param projectFile 项目root目录,一般为 "project.rootDir"
     * @return 打包临时文件夹路径
     */
    fun getBuilderFilePath(projectFile: File): File {
        return File(projectFile.absolutePath.plus("/.builder"))
    }


    /**
     * 获得渠道文件夹路径
     * @param projectFile 项目root目录,一般为 "project.rootDir"
     * @return 渠道文件夹路径
     */
    fun getChannelFilePath(projectFile: File): File {
        return File(getBuilderFilePath(projectFile).absolutePath.plus("/channels"))
    }


    /**
     * 获得渠道文件夹路径,通过渠道文件名字生成
     * @param projectFile 项目root目录,一般为 "project.rootDir"
     * @return 指定渠道的渠道文件夹路径
     */
    fun getChannelFilePathByChannelName(projectFile: File, channelName: String): File {
        return getChannelFilePath(projectFile).absolutePath.plus("/").plus(channelName).plus(".json").toFile()
    }


    /**
     * 获得项目配置文件
     * @param projectFile 项目root目录,一般为 "project.rootDir"
     * @return 返回项目config.json,用于配置项目在打包时需要设置的基本信息
     */
    fun getConfigFilePath(projectFile: File): File {
        return File(getBuilderFilePath(projectFile).absolutePath.plus("/config.json"))
    }
}