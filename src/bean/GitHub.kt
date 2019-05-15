package bean

import com.beust.klaxon.Json

data class Label(
    val id: Long,
    @Json("node_id") val nodeId: String,
    val url: String,
    val name: String,
    val color: String
)
