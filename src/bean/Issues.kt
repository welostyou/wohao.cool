package bean

import com.beust.klaxon.Json
import com.github.kittinunf.fuel.httpPost
import utils.Path
import utils.githubTimeFormat
import utils.responseString

data class Issues(
    val id: Long,
    val number: Int,
    val title: String,
    val createdAt: String,
    val body: String,
    val label: Long
)

data class IssuesJson(
    val id: Long,
    val number: Int,
    val title: String,
    @Json("created_at") val createdAt: String,
    val body: String,
    val labels: List<Label>
)

fun IssuesJson.toUseDateBase(): Issues {
    val useCreatedAt = createdAt.githubTimeFormat()
    val useLabel = labels.first().id
    val useBody = Path.githubMarkdown.httpPost().body(body).responseString!!
    return Issues(id, number, title, useCreatedAt, useBody, useLabel)
}