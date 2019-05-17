package utils

import bean.*
import com.beust.klaxon.Klaxon
import com.beust.klaxon.KlaxonException
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.httpPost
import secret.SecretField
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


object GitHub {

    object Path {
        const val label = "https://api.github.com/repos/welostyou/wohao.cool/labels"
        const val issues = "https://api.github.com/repos/welostyou/wohao.cool/issues"
    }

    object Create {

        fun label(name: String, color: String = "f2f2f2"): Boolean {
            val jsonBody = """{"name":"$name","color":"$color"}"""
            val path =
                "${Path.label}?access_token=${SecretField.GitHubAccessToken}"
            return post(path, jsonBody)
        }

        fun issue(title: String, body: String, vararg label: String): Boolean {
            val jsonBody = """{"title":"$title","body":"$body","labels":[${label.joinToString(",") { """"$it"""" }}]}"""
            val path = "${Path.issues}?access_token=${SecretField.GitHubAccessToken}"
            return post(path, jsonBody)
        }

        private fun post(path: String, jsonBody: String) =
            path.httpPost().jsonBody(jsonBody)
                .response().second.header("Status").contains("201 Created")

    }

    object Get {

        fun labels(): List<Label> {
            val path = "https://api.github.com/repos/welostyou/wohao.cool/labels"
            return path.responseJsonArray<Label>()?.filter { it.color != "f2f2f2" } ?: listOf()
        }

        fun issue(number: Int): Issue? {
            return try {
                "${Path.issues}/$number"
                    .responseString?.let {
                    Klaxon()
                        .fieldConverter(KlaxonGitHubLabel::class, labelConverter)
                        .fieldConverter(KlaxonGitHubCreatedAt::class, createdAtConverter)
                        .fieldConverter(KlaxonGitHubBody::class, bodyConverter)
                        .parse<Issue>(it)
                }
            } catch (e: Exception) {
                null
            }
        }

    }

    fun markdown(body: String): String {
        val path = "https://api.github.com/markdown/raw"
        return path.httpPost().body(body).responseString!!
    }

}

private val labelConverter by lazy {
    klaxonConverter(Label::class.java) { jsonValue ->
        jsonValue.array?.let { jsonArray ->
            Klaxon().parseFromJsonArray<Label>(jsonArray)
                ?.first { it.color != "f2f2f2" }
                ?: throw KlaxonException("data is error.")
        } ?: throw KlaxonException("label is null.")
    }
}

private val createdAtConverter by lazy {
    klaxonConverter(String::class.java) {
        it.string?.githubTimeFormat() ?: throw KlaxonException("created_at is null.")
    }
}

private val bodyConverter by lazy {
    klaxonConverter(String::class.java) {
        it.string?.let(GitHub::markdown) ?: throw KlaxonException("body is null.")
    }
}

private val githubDateFormatter by lazy {
    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.CHINA)
        .apply { timeZone = TimeZone.getTimeZone("GMT") }
}


fun String.githubTimeFormat() =
    githubDateFormatter
        .parse(this)!!
        .format("yyy-MM-dd HH:mm")
