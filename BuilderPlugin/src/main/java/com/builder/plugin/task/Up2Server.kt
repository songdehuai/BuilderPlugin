package com.builder.plugin.task

import com.builder.plugin.base.BaseTask

open class Up2Server : BaseTask() {
    override fun action() {
        println("正在将文件上传到服务器...")
        println("文件上传完成!")
    }
}