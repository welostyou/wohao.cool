package main

import github.githubWebhooks
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
 * 在获取列表的时候要在过滤时候把大小写设置一下
 *
 * 写一些指令，比如:all之类的
 */
