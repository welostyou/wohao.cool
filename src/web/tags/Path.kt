package web.tags

import kotlinx.html.*

class PATH(consumer: TagConsumer<*>) :
    HTMLTag("path", consumer, emptyMap, inlineTag = true, emptyTag = false), HtmlInlineTag {

    var fill: String? = null
        set(value) {
            if (value != null) attributes["fill"] = value
            field = value
        }

    var d: String? = null
        set(value) {
            if (value != null) attributes["d"] = value
            field = value
        }
}

fun SVG.path(block: PATH.() -> Unit = {}) {
    PATH(consumer).visit(block)
}