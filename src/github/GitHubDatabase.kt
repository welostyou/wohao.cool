package github

import utils.Database
import utils.printlnGitHubInfo
import java.sql.ResultSet

abstract class GitHubCommonDatabase : Database() {
    override val fileName = "github"
}

object GitHubDatabase {

    object Labels : GitHubCommonDatabase() {

        init {
            create("CREATE TABLE LABELS (ID INTEGER PRIMARY KEY NOT NULL, NAME TEXT NOT NULL,COLOR CHAR(6) NOT NULL)")
        }

        fun create(label: Label) = with(label) {
            insert("INSERT INTO LABELS (ID,NAME,COLOR) VALUES ($id, '$name','$color');")
            printlnGitHubInfo("新建Label：${label.name}")
        }

        fun edit(label: Label) = with(label) {
            update("UPDATE LABELS SET NAME = '$name',COLOR = '$color' WHERE ID = $id;")
            printlnGitHubInfo("修改Label：${label.name}")
        }

        fun delete(id: Long) {
            delete("DELETE FROM LABELS WHERE ID = $id;")
            printlnGitHubInfo("删除Label：$id")
        }

        operator fun get(id: Long) = select("SELECT * FROM LABELS WHERE ID = $id;") {
            Label(id, getString("NAME"), getString("COLOR"))
        }.singleOrNull()

        fun getAll() = select("SELECT * FROM LABELS;") {
            Label(getLong("ID"), getString("NAME"), getString("COLOR"))
        }

    }

    object Issues : GitHubCommonDatabase() {

        init {
            create(
                "CREATE TABLE ISSUES (" +
                        "ID INTEGER PRIMARY KEY NOT NULL, " +
                        "TITLE TEXT NOT NULL," +
                        "NUMBER INTEGER NOT NULL," +
                        "COMMENTS INTEGER NOT NULL," +
                        "LABELS TEXT NOT NULL," +
                        "CREATEDAT TEXT NOT NULL," +
                        "UPDATEDAT TEXT NOT NULL," +
                        "BODY TEXT NOT NULL)"
            )
        }

        fun open(issue: Issue) = with(issue) {
            insert(
                "INSERT INTO ISSUES (ID,TITLE,NUMBER,COMMENTS,LABELS,CREATEDAT,UPDATEDAT,BODY) " +
                        "VALUES ($id, '$title',$number,$comments,'${labels.toDatabase()}','$createdAt','$updatedAt','$body');"
            )
            printlnGitHubInfo("新建Issue:$title")
        }

        fun editComment(id: Long, comment: Int) {
            update("UPDATE ISSUES SET COMMENTS = $comment WHERE ID = $id;")
            printlnGitHubInfo("修改Comment:issueId=$id,comment=$comment")
        }

        fun edit(issue: Issue) = with(issue) {
            update(
                "UPDATE ISSUES SET TITLE = '$title'," +
                        "NUMBER = $number, " +
                        "COMMENTS = $comments, " +
                        "LABELS = '${labels.toDatabase()}', " +
                        "CREATEDAT = '$createdAt', " +
                        "UPDATEDAT = '$updatedAt', " +
                        "BODY = '$body' " +
                        "WHERE ID = $id;"
            )
            printlnGitHubInfo("修改Issue:$title")
        }

        fun delete(id: Long) {
            delete("DELETE FROM ISSUES WHERE ID = $id;")
            printlnGitHubInfo("删除Issue:$id")
        }

        fun getIssueByNumber(number: Int) =
            select("SELECT * FROM ISSUES WHERE NUMBER = $number;", ::toIssue).singleOrNull()

        operator fun get(id: Long) = select("SELECT * FROM ISSUES WHERE ID = $id;", ::toIssue).singleOrNull()

        fun getAll() = select("SELECT * FROM ISSUES;", ::toIssue)

        private fun toIssue(resultSet: ResultSet): Issue {
            val id = resultSet.getLong("ID")
            val title = resultSet.getString("TITLE")
            val number = resultSet.getInt("NUMBER")
            val comments = resultSet.getInt("COMMENTS")
            val labels = resultSet.getString("LABELS").toLabels()
            val createdAt = resultSet.getString("CREATEDAT")
            val updateAt = resultSet.getString("UPDATEDAT")
            val body = resultSet.getString("BODY")
            return Issue(id, title, number, comments, labels, createdAt, updateAt, body)
        }
    }
}
