package github

import utils.Database
import utils.printlnGitHubInfo
import java.sql.ResultSet

object GitHubIssueDatabase : Database() {

    override val fileName = "github"

    init {
        create(
            "CREATE TABLE ISSUES (" +
                    "ID INTEGER PRIMARY KEY NOT NULL, " +
                    "TITLE TEXT NOT NULL," +
                    "CREATEDAT TEXT NOT NULL," +
                    "BODY TEXT NOT NULL)"
        )
    }

    fun open(issue: Issue) = with(issue) {
        insert(
            "INSERT INTO ISSUES (ID,TITLE,CREATEDAT,BODY) " +
                    "VALUES ($id, '$title','$createdAt','$body');"
        )
        printlnGitHubInfo("添加Issue到数据库:$title")
    }

    fun edit(issue: Issue) = with(issue) {
        update(
            "UPDATE ISSUES SET TITLE = '$title'," +
                    "CREATEDAT = '$createdAt', " +
                    "BODY = '$body' " +
                    "WHERE ID = $id;"
        )
        printlnGitHubInfo("修改数据库Issue:$title")
    }

    fun delete(id: Int) {
        delete("DELETE FROM ISSUES WHERE ID = $id;")
        printlnGitHubInfo("删除数据库Issue:$id")
    }

    fun getIssueById(id: Int) =
        select("SELECT * FROM ISSUES WHERE ID = $id;", GitHubIssueDatabase::toIssue).singleOrNull()

    fun all() = select("SELECT * FROM ISSUES;", GitHubIssueDatabase::toIssue)

    private fun toIssue(resultSet: ResultSet): Issue {
        val id = resultSet.getInt("ID")
        val title = resultSet.getString("TITLE")
        val createdAt = resultSet.getString("CREATEDAT")
        val body = resultSet.getString("BODY")
        return Issue(id, title, createdAt, body)
    }

}