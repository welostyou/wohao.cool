package utils

import com.beust.klaxon.Klaxon

inline fun <reified T> String.jsonObject() = Klaxon().parse<T>(this)

inline fun <reified T> String.jsonArray() = Klaxon().parseArray<T>(this)

inline fun <reified T> String.responseJsonObject() = this.responseString?.jsonObject<T>()

inline fun <reified T> String.responseJsonArray() = this.responseString?.jsonArray<T>()

