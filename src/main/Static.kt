package main

import io.ktor.http.content.resource
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.routing.Routing

fun Routing.static() {

    static("assets") {
        resource("favicon.ico")

        static("css") {
            resources("assets/css")
        }

        static("js") {
            resources("assets/js")
        }

        static("img") {
            resources("assets/img")
        }
    }

    static("files") {
        resources("files")
    }

}