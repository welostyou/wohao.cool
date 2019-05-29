package github

import com.beust.klaxon.Json
import com.beust.klaxon.Klaxon
import com.beust.klaxon.KlaxonException
import utils.klaxonConverter
import utils.printlnGitHubWarn
import utils.responseString

@Target(AnnotationTarget.FIELD)
annotation class KlaxonGitHubDate

@Target(AnnotationTarget.FIELD)
annotation class KlaxonGitHubBody

@Suppress("OVERLOADS_WITHOUT_DEFAULT_ARGUMENTS")
data class Issue @JvmOverloads constructor(
    val id: Long,

    val title: String,
    val number: Int,
    val comments: Int,

    val labels: List<Label>,

    @Json("created_at")
    @KlaxonGitHubDate
    val createdAt: String,

    @Json("updated_at")
    @KlaxonGitHubDate
    val updatedAt: String,

    @KlaxonGitHubBody
    val body: String
)

fun getIssueByNumber(number: Int) = try {
    val path = "${GitHubApiPath.issues}/$number"
    path.responseString?.let {
        Klaxon()
            .fieldConverter(KlaxonGitHubDate::class, dateConverter)
            .fieldConverter(KlaxonGitHubBody::class, bodyConverter)
            .parse<Issue>(it)
    }
} catch (e: Exception) {
    e.message?.let(::printlnGitHubWarn)
    null
}

private val dateConverter by lazy {
    klaxonConverter(String::class.java) {
        it.string?.githubTimeFormat() ?: throw KlaxonException("created_at is null.")
    }
}

private val bodyConverter by lazy {
    klaxonConverter(String::class.java) {
        it.string?.let(::markdown) ?: throw KlaxonException("body is null.")
    }
}