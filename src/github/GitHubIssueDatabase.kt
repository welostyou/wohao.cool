package github

import utils.Database
import utils.decodeURL
import utils.encodeURL
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
                    "VALUES ($id, '$title','$createdAt','${body.encodeURL}');"
        )
    }

    fun edit(issue: Issue) = with(issue) {
        update(
            "UPDATE ISSUES SET TITLE = '$title'," +
                    "CREATEDAT = '$createdAt', " +
                    "BODY = '${body.encodeURL}' " +
                    "WHERE ID = $id;"
        )
    }

    fun delete(id: Int) {
        delete("DELETE FROM ISSUES WHERE ID = $id;")
    }

    fun getIssueById(id: Int) =
        select("SELECT * FROM ISSUES WHERE ID = $id;", GitHubIssueDatabase::toIssue).singleOrNull()

    fun all() = select("SELECT * FROM ISSUES;", GitHubIssueDatabase::toIssue)

    private fun toIssue(resultSet: ResultSet): Issue {
        val id = resultSet.getInt("ID")
        val title = resultSet.getString("TITLE")
        val createdAt = resultSet.getString("CREATEDAT")
        val body = resultSet.getString("BODY").decodeURL
        return Issue(id, title, createdAt, body)
    }

}