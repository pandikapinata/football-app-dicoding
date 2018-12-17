package com.example.pandu.finalproject.utils

import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import java.text.SimpleDateFormat
import java.util.*

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun countList(input: String?): String {
    val zero = 0
    when (input) {
        null -> return zero.toString()
    }
    val result: List<String>? = input?.split(";")

    return (result?.count()?.minus(1)).toString()
}

fun stringReplace(input: String?): String? {
    val asal = ";"
    val tujuan = "\n"
    return input?.replace(asal, tujuan)
}

fun toDatetoString(dateStr: String, timeStr: String?, mode: String) : String {
    val formatAsal = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH)
    val formatNull = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    formatAsal.timeZone = TimeZone.getTimeZone("GMT")
    val locale = Locale("in", "ID")
    val formatTujuan = SimpleDateFormat(mode, locale)
    formatTujuan.timeZone = TimeZone.getTimeZone("GMT+08:00")
    return if (timeStr == null){
        val date = formatNull.parse(dateStr)
        formatTujuan.format(date)
    }else{
        val dateTime = "$dateStr $timeStr"
        val date = formatAsal.parse(dateTime)
        formatTujuan.format(date)
    }
}

fun isNetworkAvailable(ctx: Context?): Boolean {
    val connectivityManager = ctx?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    return if (connectivityManager is ConnectivityManager) {
        val networkInfo = connectivityManager.activeNetworkInfo
        networkInfo?.isConnected ?: false
    } else {
        false
    }
}