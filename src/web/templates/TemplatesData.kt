package web.templates

data class TemplatesData(
    val allLabels: String,
    val update10: String,
    val count: Int,

    val title: String,
    val number: Int,
    val labels: String,
    val createdAt: String,
    val updatedAt: String,
    val body: String,
    val comments: Int,

    val bodyLength: Int,

    val index: String = "https://wohao.cool",
    val design: String = "/collection/design",
    val about: String = "/about",
    val website: String = "/collection/website",
    val githubRepo: String = "https://github.com/welostyou/wohao.cool",
    val githubStyles: String = "https://styleguide.github.com/primer",
    val githubMy: String = "https://github.com/welostyou",
    val avatars: String = "/assets/img/avatars.jpg"
)
