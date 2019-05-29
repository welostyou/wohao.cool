package main

import com.fasterxml.jackson.databind.SerializationFeature
import freemarker.cache.ClassTemplateLoader
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.features.StatusPages
import io.ktor.freemarker.FreeMarker
import io.ktor.http.HttpStatusCode
import io.ktor.jackson.jackson
import io.ktor.response.respond
import io.ktor.routing.Routing

fun Application.applicationInstall() {

    install(FreeMarker) { templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates") }

    install(ContentNegotiation) { jackson { enable(SerializationFeature.INDENT_OUTPUT) } }

//    install(HttpsRedirect) {
//        sslPort = 443
//        permanentRedirect = true
//    }

}

fun Routing.routingInstall() {
    install(StatusPages) {
        exception<AuthenticationException> {
            call.respond(HttpStatusCode.Unauthorized)
        }
        exception<AuthorizationException> {
            call.respond(HttpStatusCode.Forbidden)
        }
    }
}

class AuthenticationException : RuntimeException()
class AuthorizationException : RuntimeException()
