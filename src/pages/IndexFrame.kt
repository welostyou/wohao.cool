package pages

import database.LabelsDatabase
import io.ktor.application.call
import io.ktor.html.respondHtml
import io.ktor.routing.Routing
import io.ktor.routing.get
import kotlinx.html.*
import utils.cssFile
import utils.htmlHeadDefaultConfigure
import utils.jsFile

fun Routing.indexFrame() {

    get("/test") {
        call.respondHtml {
            head {
                htmlHeadDefaultConfigure("那你生气吧")
                cssFile("index-frame")
                jsFile("index-frame")
            }
            body {
                div("left") {
                    textInput {
                        autoComplete = false
                        placeholder = "搜索..."
                        onKeyUp = "searchOnKeyUp(this)"
                    }

                    ul {
                        LabelsDatabase.getAll().forEach {
                            li {
                                span {
                                    style = "color: #${it.color}"
                                    +"●"
                                }
                                span("label-name") { +it.name }
                            }
                        }
                    }
                }
                div("center") { }
                div("right") { }
            }
        }
    }

}