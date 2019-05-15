package main

import io.ktor.application.call
import io.ktor.html.respondHtml
import io.ktor.routing.Routing
import io.ktor.routing.get
import kotlinx.html.*
import utils.htmlHeadDefaultConfigure
import utils.cssFile

const val INDEX_NAME = "那你生气吧"
const val DESIGN_NAME = "那你生气吧 · 设计"

const val URL_API = "https://github.com/welostyou/wohao.cool/wiki"
const val URL_REPO = "https://github.com/welostyou?tab=stars"
const val URL_DESIGN = "/design"
const val URL_ABOUT = "https://github.com/welostyou/wohao.cool/blob/master/README.md"

fun Routing.pages() {

    get("/") {
        call.respondHtml {
            head {
                htmlHeadDefaultConfigure(INDEX_NAME)
                cssFile("index")
            }
            body {
                div { h1 { +INDEX_NAME } }
                div {
                    p {
                        a(URL_DESIGN) { +"Design" }
                        +"·"
                        a(URL_API) { +"Api" }
                        +"·"
                        a(URL_REPO) { +"GitHub Stars" }
                        +"·"
                        a(URL_ABOUT) { +"About" }
                    }
                }
            }
        }
    }

    get("/design") {
        call.respondHtml {
            head {
                htmlHeadDefaultConfigure(DESIGN_NAME)
                cssFile("design")
            }
            body { +"开发中..." }
        }
    }
}