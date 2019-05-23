package github

data class Label(val id: Long, val name: String, val color: String)

fun List<Label>.toDatabase() = joinToString(",") { it.id.toString() }

fun String.toLabels() = split(",").map {
    GitHubDatabase.Labels[it.toLong()] ?: throw Exception("Label is not find.")
}