package utils

import java.text.SimpleDateFormat
import java.util.*

fun Any.print() = print(this)

fun Any.println() = println(this)

fun Date.format(pattern: String) = SimpleDateFormat(pattern, Locale.CHINA).format(this)!!

val isWindows10 = System.getProperty("os.name") == "Windows 10"