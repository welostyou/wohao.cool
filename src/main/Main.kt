package main

import api.apiOfLive
import api.apiOfTodayInHistory
import io.ktor.application.Application
import io.ktor.routing.routing
import pages.indexFrame
import secret.applicationShutdown

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
@kotlin.jvm.JvmOverloads
fun Application.module(@Suppress("UNUSED_PARAMETER") testing: Boolean = false) {

    applicationInstall()
    applicationShutdown()

    routing {

        routingInstall()
        static()
        pages()

        apiOfLive()
        apiOfTodayInHistory()

        indexFrame()

    }
}

