package com.builder.plugin.ext

import com.google.gson.GsonBuilder

private val gson = GsonBuilder().setPrettyPrinting().create()

fun Any?.toJson(): String {
    return gson.toJson(this)
}