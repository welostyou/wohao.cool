package main

import github.webhooks.githubWebhooks
import io.ktor.application.Application
import io.ktor.routing.routing
import secret.applicationShutdown
import web.api.apiOfLive
import web.api.apiOfTodayInHistory
import web.pages

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
@kotlin.jvm.JvmOverloads
fun Application.module(@Suppress("UNUSED_PARAMETER") testing: Boolean = false) {

    applicationInstall()
    applicationShutdown()

    routing {

        routingInstall()
        static()

        apiOfLive()
        apiOfTodayInHistory()

        githubWebhooks()

        pages()
    }
}

/**
 * 如果转服务器，要把https重定向的安装取消注释，并且要把GitHubApiPath中repo改回wohao.cool
 */
