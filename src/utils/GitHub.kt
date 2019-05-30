package utils

import com.github.kittinunf.fuel.Fuel
import github.GitHubApiPath
import java.nio.charset.Charset
import java.text.SimpleDateFormat
import java.util.*

fun markdown(body: String) = Fuel.post(GitHubApiPath.markdown).body(body).responseString

private val githubDateFormatter by lazy {
    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.CHINA)
        .apply { timeZone = TimeZone.getTimeZone("GMT") }
}

fun String.githubTimeFormat() =
    githubDateFormatter
        .parse(this)!!
        .format("yyy-MM-dd")

val String.UTF8
    get() = String(
        this.toByteArray(Charset.forName("ISO-8859-1")),
        Charset.forName("UTF-8")
    )