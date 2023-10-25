package com.codigo.dailyvita.utils

import android.content.Context
import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader

fun readJSONFromAssets(context: Context, path: String): String {
    val tag = "[ReadJSON]"
    try {
        val file = context.assets.open("$path")
        Log.i(
            tag,
            "Found File: $file.",
        )
        val bufferedReader = BufferedReader(InputStreamReader(file))
        val stringBuilder = StringBuilder()
        bufferedReader.useLines { lines ->
            lines.forEach {
                stringBuilder.append(it)
            }
        }
        Log.i(
            tag,
            "getJSON stringBuilder: $stringBuilder.",
        )
        val jsonString = stringBuilder.toString()
        Log.i(
            tag,
            "JSON as String: $jsonString.",
        )
        return jsonString
    } catch (e: Exception) {
        Log.e(
            tag,
            "Error reading JSON: $e.",
        )
        e.printStackTrace()
        return ""
    }
}