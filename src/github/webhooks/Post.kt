package github.webhooks

import com.fasterxml.jackson.databind.ObjectMapper
import github.Label
import github.getIssueByNumber
import io.ktor.application.call
import io.ktor.request.header
import io.ktor.request.receiveText
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.post
import utils.jsonObject
import utils.println
import java.lang.Exception

fun Routing.githubWebhooks() {

    post("/github/webhooks") {
        val payload = call.receiveText()
        val event = call.request.header("X-GitHub-Event")?.toUpperCase()?.let(Event::valueOf)
        call.respondText("ojerk!")
        when (event) {
            Event.ISSUES, Event.ISSUE_COMMENT -> {
                val issuePayload = getIssuesPayload(payload)
                issuePayload?.println()
            }
            Event.LABEL -> {
                val labelPayload = getLabelPayload(payload)
                labelPayload.println()
            }
        }
    }
}

fun getIssuesPayload(json: String) = ObjectMapper().readTree(json).let { payload ->
    val action = try {
        IssuesAction.valueOf(payload.path("action").asText().toUpperCase())
    } catch (e: Exception) {
        println("Issues action not opened, edited or deleted.")
        null
    }
    payload.path("issue").let { issue ->
        val number = issue.path("number").asInt()
        val isMe = issue.path("user").path("login").asText() == "welostyou"
        if (isMe && action != null) IssuePayload(action, getIssueByNumber(number)) else null
    }
}

fun getLabelPayload(json: String) = ObjectMapper().readTree(json).let { payload ->
    val action = LabelAction.valueOf(payload.path("action").asText().toUpperCase())
    val label = payload.path("label").toString().jsonObject<Label>()!!
    LabelPayload(action, label)
}