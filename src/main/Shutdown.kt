package main

import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.server.engine.ShutDownUrl

fun Application.applicationShutdown() {
    install(ShutDownUrl.ApplicationCallFeature) {
        shutDownUrl = "/shutdown/94a64ef14d18f64ce60be18e88547ab3"
        exitCodeSupplier = { 0 } // ApplicationCall.() -> Int
    }
}