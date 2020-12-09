package com.builder.plugin.ext

import com.google.gson.Gson

val gson = Gson()

fun Any?.toJson(): String {
    return gson.toJson(this)
}