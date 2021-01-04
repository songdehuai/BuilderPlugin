package com.builder.plugin.ext

import java.io.File


fun String.toFile(): File {
    return File(this)
}