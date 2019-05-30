package github

import com.beust.klaxon.Json
import com.beust.klaxon.Klaxon
import com.beust.klaxon.KlaxonException
import utils.klaxonConverter
import utils.printlnGitHubErr
import utils.responseString

enum class IssueAction {
    OPENED,
    EDITED,
    CLOSED,
    DELETED,
    REOPENED,

    TRANSFERRED,
    PINNED,
    UNPINNED,
    ASSIGNED,
    UNASSIGNED,
    LABELED,
    UNLABELED,
    LOCKED,
    UNLOCKED,
    MILESTONED,
    DEMILESTONED
}

data class IssuePayload(val action: IssueAction, val issue: Issue?, val id: Int)

@Target(AnnotationTarget.FIELD)
annotation class KlaxonGitHubDate

@Target(AnnotationTarget.FIELD)
annotation class KlaxonGitHubBody

@Suppress("OVERLOADS_WITHOUT_DEFAULT_ARGUMENTS")
data class Issue @JvmOverloads constructor(

    @Json("number")
    val id: Int,
    val title: String,

    @Json("created_at")
    @KlaxonGitHubDate
    val createdAt: String,

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
    e.message?.let(::printlnGitHubErr)
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
