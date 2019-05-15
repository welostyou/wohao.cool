package utils

import java.text.SimpleDateFormat
import java.util.*

fun toDay(pattern: String = "yyyy-MM-dd") = SimpleDateFormat(pattern).format(Date())

fun toDayOfLong() = Date().time