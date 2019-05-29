package web.tags

import kotlinx.html.*

class SVG(initialAttributes: Map<String, String>, consumer: TagConsumer<*>) :
    HTMLTag("svg", consumer, initialAttributes, inlineTag = true, emptyTag = false), HtmlInlineTag {

    var viewBox: String? = null
        set(value) {
            if (value != null) attributes["viewBox"] = value
            field = value
        }

    var height: String? = null
        set(value) {
            if (value != null) attributes["height"] = value
            field = value
        }

    var width: String? = null
        set(value) {
            if (value != null) attributes["width"] = value
            field = value
        }

    var classes: String? = null
        set(value) {
            if (value != null) attributes["class"] = value
            field = value
        }
}

fun DIV.svg(block: SVG.() -> Unit = {}) {
    SVG(emptyMap, consumer).visit(block)
}