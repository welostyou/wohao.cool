package web.templates

import github.Label
import io.ktor.freemarker.FreeMarkerContent

fun getFreeMarkerContent(data: TemplatesData) =
    FreeMarkerContent("article.ftl", mapOf("data" to data), "")

val Label.rTemplatesText
    get() = """
        <span class="State mb-2 f5" style="background-color: #$color">
        <a class="text-white" href="/labels/$id">$name</a>
        </span>
    """

val Label.aTemplatesText
    get() = """
        <span class="State mr-1 f5" style="background-color: #$color">
        <a class="text-white" href="/labels/$id">$name</a>
        </span>
    """

/** update
<li><a href="#">12312312</a></li>
<li><a href="#">12312312</a></li>
 */