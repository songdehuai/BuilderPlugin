package com.builder.plugin.bean

import com.builder.plugin.config.BuilderConfig
import org.apache.commons.io.FileUtils
import java.io.File


fun getTasks(projectFile: File): ArrayList<String> {
    val tasks = arrayListOf<String>()
    FileUtils.readFileToString(BuilderConfig.getTaskFile(projectFile), BuilderConfig.CHARESET).reader().forEachLine {
        tasks.add(it)
    }
    return tasks
}
