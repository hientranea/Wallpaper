package com.hientran.wallpaper.common.extensions

import java.util.Locale.getDefault

fun String.makeCapitalize() : String {
    return this.replaceFirstChar { if (it.isLowerCase()) it.titlecase(getDefault()) else it.toString() }
}
