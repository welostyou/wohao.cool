package utils

inline fun <reified T> databasePath() = "database/${T::class.java.simpleName}.db"

object Path {

    const val githubLabels = "https://api.github.com/repos/welostyou/wohao.cool/labels"
    const val githubMarkdown = "https://api.github.com/markdown/raw"
    fun githubIssues(number:Int) = "https://api.github.com/repos/welostyou/wohao.cool/issues/$number"
}