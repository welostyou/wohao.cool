package api

import com.fasterxml.jackson.databind.ObjectMapper
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import org.jsoup.Jsoup
import java.net.URL

fun Routing.apiOfLive() {
    get("/api/live/{platform}/{room_id}") {
        call.apply {
            val platform = parameters["platform"]
            val id = parameters["room_id"]
            if (platform != null && id != null) {
                try {
                    when (platform) {
                        "douyu" -> respond(mapOf("state" to "success", "result" to getDouyuRoomIsLive(id)))
                        "kuaishou" -> respond(mapOf("state" to "success", "result" to getKuaishouRoomIsLive(id)))
                        "longzhu" -> respond(mapOf("state" to "success", "result" to getLongzhuRoomIsLive(id)))
                        "huya" -> respond(mapOf("state" to "success", "result" to getHuyaRoomIsLive(id)))
                        else -> respond(mapOf("state" to "fail", "result" to "The request failed: Error Platform."))
                    }
                } catch (e: Exception) {
                    respond(mapOf("state" to "fail", "result" to "The request failed: Room ID does not exist."))
                }
            } else {
                respond(mapOf("state" to "fail", "result" to "The request failed: Parameters cannot be null."))
            }
        }
    }
}

fun getKuaishouRoomIsLive(roomId: String): Boolean {
    val url = "https://live.kuaishou.com/search/?keyword=$roomId"
    return Jsoup.parse(URL(url).readText())
        .getElementsByClass("living-tag")
        .text() == "直播中"
}

fun getDouyuRoomIsLive(roomId: String): Boolean {
    val url = "https://www.douyu.com/search/?kw=$roomId"
    return Jsoup.parse(URL(url).readText())
        .getElementsByClass("frames")
        .text() == "正在直播"
}

fun getLongzhuRoomIsLive(roomId: String): Boolean {
    val jsonLongzhu = URL("http://searchapi.longzhu.com/api/search/misc?key=$roomId").readText()
    return ObjectMapper().readTree(jsonLongzhu)
        .path("data")
        .path("hosts")
        .first()
        .path("live")
        .path("isLive")
        .asBoolean()
}

fun getHuyaRoomIsLive(roomId: String): Boolean {
    val jsonHuya =
        URL("https://search.cdn.huya.com/?m=Search&do=getSearchContent&q=$roomId&typ=-5&rows=16").readText()
    return ObjectMapper().readTree(jsonHuya)
        .path("response")
        .path("1")
        .path("docs")
        .first()
        .path("gameLiveOn")
        .asBoolean()
}