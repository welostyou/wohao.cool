package utils

import kotlinx.html.*

fun HEAD.htmlHeadDefaultConfigure(title: String? = null) {
    if (title != null) title { +title }
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
    styleLink("/assets/css/default.css")
}

fun HEAD.cssFile(fileName: String) {
    styleLink("/assets/css/$fileName.css")
}

fun HEAD.jsFile(fileName: String) {
    script(type = "text/javascript", src = "/assets/js/$fileName.js", block = {})
}