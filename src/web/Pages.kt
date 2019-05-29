package web

import io.ktor.application.call
import io.ktor.routing.Routing
import io.ktor.routing.get

fun Routing.pages() {

    get("/") {
        respondSearchPage("请输入内容：关键字搜索&指令", "/search-result/")
    }

    get("/search-result/{value}") {
        val value = call.parameters["value"]
        val list = kotlinx.html.emptyMap
        respondListPage("共${list.size}个搜索结果：$value", list)
    }

    get("/markdown/{value}") {
        //val value = call.parameters["value"]
        val title = ""
        val markdown = ""
        respondMarkdownPage(title, markdown)
    }
}

