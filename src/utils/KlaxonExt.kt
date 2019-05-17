package utils

import com.beust.klaxon.Converter
import com.beust.klaxon.JsonValue
import com.beust.klaxon.Klaxon

inline fun <reified T> String.jsonObject() = Klaxon().parse<T>(this)

inline fun <reified T> String.jsonArray() = Klaxon().parseArray<T>(this)

inline fun <reified T> String.responseJsonObject() = this.responseString?.jsonObject<T>()

inline fun <reified T> String.responseJsonArray() = this.responseString?.jsonArray<T>()

fun <T> klaxonConverter(clazz: Class<T>, toJson: String = "", block: (JsonValue) -> T) =
    object : Converter {

        override fun canConvert(cls: Class<*>) = cls == clazz

        override fun fromJson(jv: JsonValue) = block(jv)

        override fun toJson(value: Any) = toJson

    }

