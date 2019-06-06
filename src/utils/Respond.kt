package utils

import io.ktor.application.call
import io.ktor.html.respondHtml
import kotlinx.html.*
import web.tags.path
import web.tags.svg

suspend fun PContext.respondSearchPage(placeholder: String, path: String, list: Map<String, String>? = null) {
    call.respondHtml {
        head {
            default()
            script(type = "text/javascript") {
                unsafe {
                    raw("""function searchOnKeyUp(ev) {var e = event || window.event || arguments.callee.caller.arguments[0];if (e && e.keyCode === 13) {var s = ev.value;if(s.length !== 0){ev.value = "";window.location.href = "$path" + s;}}}""")
                }
            }
        }
        body {
            div("container") {
                div("header") {
                    logo()
                    input(classes = "search", type = InputType.text) {
                        onKeyUp = "searchOnKeyUp(this)"
                        this.placeholder = placeholder
                    }
                }
                if (list != null) {
                    ul("list") {
                        list.forEach { href, title ->
                            li { a(href) { +title } }
                        }
                    }
                }
            }
        }
    }
}

suspend fun PContext.respondListPage(title: String, list: Map<String, String>) {
    call.respondHtml {
        head { default() }
        body {
            div("container") {
                textHeader(title)
                ul("list") {
                    list.forEach { href, title ->
                        li { a(href) { +title } }
                    }
                }
            }
        }
    }
}

suspend fun PContext.respondMarkdownPage(title: String = "", body: String = "") {
    call.respondHtml {
        head {
            default()
            styleLink("/assets/css/github-markdown.css")
        }
        body {
            div("container") {
                textHeader(title)
                div("markdown-body") { unsafe { raw(body) } }
            }
        }
    }
}

private fun HEAD.default() {
    title("那你生气吧")
    link {
        rel = "icon"
        href = "/assets/favicon.ico"
        type = "image/x-icon"
    }
    link {
        rel = "shortcut icon"
        href = "/assets/favicon.ico"
        type = "image/x-icon"
    }
    styleLink("https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i")
    styleLink("/assets/css/style.css")
}

private fun DIV.textHeader(title: String) {
    div("header") {
        logo()
        p("title") {
            +if (title.isEmpty()) "没有内容" else title
        }
    }
}

private fun DIV.logo(classes: String = "float-left logo") {
    svg {
        viewBox = "0 0 1024 1024"
        height = "48"
        width = "48"
        this.classes = classes

        path {
            fill = "#24292e"
            d =
                "M512 831.82c-47.48 0-93.56-9.31-136.96-27.66-41.9-17.72-79.52-43.09-111.83-75.39-32.3-32.3-57.67-69.93-75.39-111.83-18.35-43.4-27.66-89.48-27.66-136.96s9.31-93.56 27.66-136.96c17.72-41.9 43.09-79.52 75.39-111.83 32.3-32.3 69.92-57.67 111.83-75.39 43.4-18.35 89.48-27.66 136.96-27.66s93.56 9.31 136.96 27.66c41.9 17.72 79.52 43.09 111.83 75.39 32.3 32.3 57.67 69.93 75.39 111.83 18.36 43.4 27.66 89.48 27.66 136.96s-9.31 93.56-27.66 136.96c-17.72 41.9-43.09 79.52-75.39 111.83-32.3 32.3-69.93 57.67-111.83 75.39-43.4 18.35-89.48 27.66-136.96 27.66z m0-639.67c-158.71 0-287.84 129.12-287.84 287.84S353.29 767.82 512 767.82 799.84 638.7 799.84 479.98 670.71 192.15 512 192.15z"
        }

        path {
            fill = "#24292e"
            d = "M576 640H448c-17.67 0-32-14.33-32-32s14.33-32 32-32h128c17.67 0 32 14.33 32 32s-14.32 32-32 32z"
        }
    }
}