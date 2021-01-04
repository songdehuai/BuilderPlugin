package com.builder.plugin.config

import com.builder.plugin.ext.toFile
import java.io.File

object BuilderConfig {

    const val CHARESET = "UTF-8"

    const val TASK_FILE_NAME = "task.txt"

    const val CHANNELS_NAME = "channels"

    const val CHANNEL_NAME = "channel.json"

    const val BUILDER_NAME = ".builder"

    const val CONFIG_NAME = "config.json"

    /**
     * 获得打包临时文件夹路径
     * @param projectFile 项目root目录,一般为 "project.rootDir"
     * @return 打包临时文件夹路径
     */
    fun getBuilderFile(projectFile: File): File {
        return "${projectFile.absolutePath}/${BUILDER_NAME}".toFile()
    }

    /**
     * 获得task文件
     * @param projectFile 项目root目录,一般为 "project.rootDir"
     * @return task文件,默认在 .builder/task.txt
     */
    fun getTaskFile(projectFile: File): File {
        return "${getBuilderFile(projectFile)}/${TASK_FILE_NAME}".toFile()
    }

    /**
     * 获得渠道文件夹路径
     * @param projectFile 项目root目录,一般为 "project.rootDir"
     * @return 渠道文件夹路径
     */
    fun getChannelFile(projectFile: File): File {
        return "${getBuilderFile(projectFile)}/${CHANNELS_NAME}".toFile()
    }

    /**
     * 获得渠道文件夹路径
     * @param projectFile 项目root目录,一般为 "project.rootDir"
     * @return 渠道文件夹路径
     */
    fun getChannelFileByName(projectFile: File, channelName: String): File {
        return "${getBuilderFile(projectFile).absolutePath}/${CHANNELS_NAME}/${channelName}".toFile()
    }


    /**
     * 获得渠道文件夹路径,通过渠道文件名字生成
     * @param projectFile 项目root目录,一般为 "project.rootDir"
     * @return 指定渠道的渠道文件夹路径
     */
    fun getChannelFileByChannelName(projectFile: File, channelName: String): File {
        return "${getChannelFile(projectFile).absolutePath}/${channelName}/${CHANNEL_NAME}".toFile()
    }

    /**
     * 获得渠道文件夹路径,通过渠道文件名字生成
     * @param projectFile 项目root目录,一般为 "project.rootDir"
     * @return 指定渠道的渠道文件夹路径
     */
    fun getChannelFilePathByChannelName(projectFile: File, channelName: String): String {
        return "${getChannelFile(projectFile).absolutePath}/${channelName}/${CHANNEL_NAME}"
    }


    /**
     * 获得项目配置文件
     * @param projectFile 项目root目录,一般为 "project.rootDir"
     * @return 返回项目config.json,用于配置项目在打包时需要设置的基本信息
     */
    fun getConfigFile(projectFile: File): File {
        return "${getBuilderFile(projectFile).absolutePath}/${CONFIG_NAME}".toFile()
    }


    /**
     * 获得相对于项目目录下的文件
     * @param projectFile 项目root目录,一般为 "project.rootDir"
     * @param path 相对于项目目录下的文件
     * @return 返回相对于项目目录下的文件
     */
    fun getProjectFileByPath(projectFile: File, path: String): File {
        return "${projectFile.absolutePath}${path}".toFile()
    }

    /**
     * 获得相对于渠道目录下的文件
     * @param projectFile 项目root目录,一般为 "project.rootDir"
     * @param path 相对于渠道目录下的文件
     * @return 返回相对于渠道目录下的文件
     */
    fun getChannelFileByPath(projectFile: File, channelName: String, path: String): File {
        return "${getChannelFileByName(projectFile, channelName).absolutePath}${path}".toFile()
    }
}