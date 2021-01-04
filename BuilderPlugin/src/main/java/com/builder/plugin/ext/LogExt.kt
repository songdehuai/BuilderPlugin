package com.builder.plugin.ext

import java.text.SimpleDateFormat
import java.util.*


fun Any?.log() {
    println("**********************************************************************************************************************************************************************************************************************")
    print("*\n*\n*")
    println("    $this")
    println("*    ${nowTime()}")
    print("*\n*\n")
    println("**********************************************************************************************************************************************************************************************************************")
}

fun Any?.logE() {
    println("\u001b[0;35m**********************************************************************************************************************************************************************************************************************\u001b[0m")
    print("\u001b[0;35m*\n*\n*\u001b[0m")
    println("\u001b[0;35m    $this\u001b[0m")
    println("\u001b[0;35m*    ${nowTime()}\u001b[0m")
    print("\u001b[0;35m*\n*\n\u001b[0m")
    println("\u001b[0;35m**********************************************************************************************************************************************************************************************************************\u001b[0m")
}


@Suppress("SimpleDateFormat")
fun nowTime(format: String? = "yyyy-MM-dd HH:mm:ss"): String {
    val sdf = SimpleDateFormat(format)
    return sdf.format(Date())
}
