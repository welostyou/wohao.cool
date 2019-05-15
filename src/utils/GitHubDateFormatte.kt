package utils

import java.text.SimpleDateFormat
import java.util.*

val githubDateFormatter by lazy {
    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.CHINA)
        .apply {
            timeZone = TimeZone.getTimeZone("GMT")
        }
}

val outputDateFormatter by lazy {
    SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA)
}

fun Date.format(pattern: String) = SimpleDateFormat(pattern, Locale.CHINA).format(this)!!

fun githubTimeToDate(time: String) = githubDateFormatter.parse(time)!!

fun Date.view(): String {
    val now = System.currentTimeMillis()
    val seconds = (now - this.time) / 1000
    val minutes = seconds / 60
    return when {
        minutes >= 60 -> {
            val hours = minutes / 60
            when {
                hours in 24..47 -> "昨天"
                hours >= 48 -> outputDateFormatter.format(this)
                else -> "${hours}小时前"
            }
        }
        minutes < 1 -> "${seconds}秒前"
        else -> "${minutes}分钟前"
    }
}

fun String.githubTimeFormat() = githubTimeToDate(this).format("yyy-MM-dd HH:mm")
fun String.githubTimeAfter() = outputDateFormatter.parse(this).view()

fun main() {
    "2019-05-15T08:44:29Z".githubTimeFormat().println()
    "2019-05-15 16:44".githubTimeAfter().println()

}