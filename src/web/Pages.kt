package web

import github.GitHubIssueDatabase
import io.ktor.application.call
import io.ktor.routing.Routing
import io.ktor.routing.get
import utils.println
import utils.respondListPage
import utils.respondMarkdownPage
import utils.respondSearchPage

fun Routing.pages() {

    get("/") {
        val list = hashMapOf<String, String>()
        GitHubIssueDatabase.all().forEach { list["/markdown/github/${it.id}"] = it.title }
        respondSearchPage("请输入内容：关键字搜索&指令", "/search-result/", list)
    }

    get("/search-result/{value}") {
        val value = call.parameters["value"]
        val list = hashMapOf<String, String>()
        if (!value.isNullOrEmpty()) {
            GitHubIssueDatabase.all()
                .filter { it.title.toUpperCase().contains(value.toUpperCase()) }
                .forEach { list["/markdown/github/${it.id}"] = it.title }
            respondListPage("共${list.size}个搜索结果：$value", list)
        }
    }

    get("/markdown/github/{value}") {
        val value = call.parameters["value"]?.toInt()
        if (value != null) {
            GitHubIssueDatabase.getIssueById(value)?.apply { respondMarkdownPage(title, body) }
        } else {
            respondMarkdownPage()
        }
    }
}