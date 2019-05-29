package github.webhooks

import com.fasterxml.jackson.databind.ObjectMapper
import github.GitHubDatabase
import github.Label
import github.getIssueByNumber
import io.ktor.application.call
import io.ktor.request.header
import io.ktor.request.receiveText
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.post
import utils.jsonObject
import utils.printlnGitHubWarn

fun Routing.githubWebhooks() {

    post("/github/webhooks") {
        val payload = call.receiveText()
        val event = call.request.header("X-GitHub-Event")?.toUpperCase()?.let(Event::valueOf)
        call.respondText("ojerk!")
        when (event) {
            Event.ISSUE_COMMENT -> getIssuePayload(payload)?.also { (action, issue) ->
                issue?.let {
                    when (action) {
                        IssueAction.CREATED, IssueAction.DELETED ->
                            GitHubDatabase.Issues.editComment(it.id, it.comments)
                        else -> null
                    }
                } ?: printlnGitHubWarn("Issue 获取失败")
            } ?: printlnGitHubWarn("Issue 并不是管理员创建的")

            Event.ISSUES -> getIssuePayload(payload)?.also { (action, issue) ->
                issue?.let {
                    when (action) {
                        IssueAction.OPENED -> GitHubDatabase.Issues.open(issue)
                        IssueAction.DELETED, IssueAction.TRANSFERRED -> GitHubDatabase.Issues.delete(issue.id)
                        else -> GitHubDatabase.Issues.edit(issue)
                    }
                } ?: printlnGitHubWarn("Issue 获取失败")
            } ?: printlnGitHubWarn("Issue 并不是管理员创建的")

            Event.LABEL -> getLabelPayload(payload).also { (action, label) ->
                when (action) {
                    LabelAction.DELETED -> GitHubDatabase.Labels.delete(label.id)
                    LabelAction.EDITED -> GitHubDatabase.Labels.edit(label)
                    LabelAction.CREATED -> GitHubDatabase.Labels.create(label)
                }
            }
        }
    }
}

fun getIssuePayload(json: String) = ObjectMapper().readTree(json).let { payload ->
    val action = IssueAction.valueOf(payload.path("action").asText().toUpperCase())
    payload.path("issue").let { issue ->
        val number = issue.path("number").asInt()
        val isMe = issue.path("user").path("login").asText() == "welostyou"
        if (isMe) IssuePayload(action, getIssueByNumber(number)) else null
    }
}

fun getLabelPayload(json: String) = ObjectMapper().readTree(json).let { payload ->
    val action = LabelAction.valueOf(payload.path("action").asText().toUpperCase())
    val label = payload.path("label").toString().jsonObject<Label>()!!
    LabelPayload(action, label)
}