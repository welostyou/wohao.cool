package bean

import com.beust.klaxon.Json

@Target(AnnotationTarget.FIELD)
annotation class KlaxonGitHubLabel

@Target(AnnotationTarget.FIELD)
annotation class KlaxonGitHubCreatedAt

@Target(AnnotationTarget.FIELD)
annotation class KlaxonGitHubBody

@Suppress("OVERLOADS_WITHOUT_DEFAULT_ARGUMENTS")
data class Issue @JvmOverloads constructor(
    val id: Long,
    val title: String,
    val number: Int,
    val comments: Int,

    @KlaxonGitHubLabel
    @Json("labels")
    val label: Label,

    @Json("created_at")
    @KlaxonGitHubCreatedAt
    val createdAt: String,

    @Json("html_url")
    val htmlUrl: String,

    @KlaxonGitHubBody
    val body: String
)