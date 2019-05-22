package web.api

import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import org.jsoup.Jsoup
import java.net.URL
import java.util.*

fun Routing.apiOfTodayInHistory() {
    get("/web/api/today-in-history/{month}/{day}") {
        call.apply {
            val month = parameters["month"]
            val day = parameters["day"]
            if (month != null && day != null) {
                try {
                    val html = URL("http://www.lssdjt.com/$month/$day/").readText()
                    val result = Collections.synchronizedList(
                        Jsoup.parse(html)
                            .select("li.gong")
                            .map {
                                TodayInHistoryResult(
                                    it.getElementsByTag("em").text(),
                                    it.getElementsByTag("i").text()
                                )
                            })
                    respond(mapOf("state" to "success", "result" to result))
                } catch (e: Exception) {
                    respond(mapOf("state" to "fail", "result" to "The request failed: Please check the parameters."))
                }
            } else {
                respond(mapOf("state" to "fail", "result" to "The request failed: Parameters cannot be null."))
            }
        }
    }
}

data class TodayInHistoryResult(val date: String, val event: String)