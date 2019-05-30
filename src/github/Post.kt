package github

import com.fasterxml.jackson.databind.ObjectMapper
import io.ktor.application.call
import io.ktor.request.header
import io.ktor.request.receiveText
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.post

fun Routing.githubWebhooks() {

    post("/github/webhooks") {
        val payload = call.receiveText()
        val event = call.request.header("X-GitHub-Event") == "issues"
        call.respondText("ojerk!")
        val issuePayload = if (event) getIssuePayload(payload) else null
        issuePayload?.let {
            when (it.action) {
                IssueAction.CLOSED,
                IssueAction.DELETED -> {
                    GitHubIssueDatabase.delete(it.id)
                }
                IssueAction.REOPENED,
                IssueAction.OPENED -> {
                    if (it.issue != null) GitHubIssueDatabase.open(it.issue)
                }
                IssueAction.EDITED -> {
                    if (it.issue != null) GitHubIssueDatabase.edit(it.issue)
                }
                else -> {
                    println("Issue其他改动：${it.action}")
                }
            }
        }
    }
}

private fun getIssuePayload(json: String) = ObjectMapper().readTree(json).let { payload ->
    val action = IssueAction.valueOf(payload.path("action").asText().toUpperCase())
    payload.path("issue").let { issue ->
        val number = issue.path("number").asInt()
        when (action) {
            IssueAction.CLOSED, IssueAction.DELETED -> {
                IssuePayload(action, null, number)
            }
            else -> {
                val isMe = issue.path("user").path("login").asText() == "welostyou"
                if (isMe) IssuePayload(action, getIssueByNumber(number), number) else null
            }
        }
    }
}