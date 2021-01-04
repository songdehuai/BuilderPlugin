package com.builder.plugin.ext

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

val gson = GsonBuilder().setPrettyPrinting().create()

fun Any?.toJson(): String {
    return gson.toJson(this)
}

inline fun <reified T> Any?.toBean(): T {
    return gson.fromJson(this.toString(), object : TypeToken<T>() {}.type)
}