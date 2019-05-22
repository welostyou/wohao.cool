package github

data class Label(val id: Long, val name: String, val color: String)

val List<Label>.database get() = joinToString(",") { it.id.toString() }

val String.labels get() = split(",").map(String::toLong)