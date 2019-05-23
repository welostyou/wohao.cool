package github

import utils.Database

abstract class GitHubCommonDatabase : Database() {
    override val fileName = "github"
}

object GitHubDatabase {

    object Comments : GitHubCommonDatabase() {

        init {
            create("CREATE TABLE COMMENTS (ID INTEGER PRIMARY KEY NOT NULL, COMMENT INTEGER NOT NULL)")
        }

        fun create(id: Long, comment: Int) = insert("INSERT INTO COMMENTS (ID,COMMENT) VALUES ($id, $comment);")

        fun update(id: Long, comment: Int) = update("UPDATE COMMENTS SET COMMENT = $comment WHERE ID = $id;")

        fun delete(id: Long) = delete("DELETE FROM COMMENTS WHERE ID = $id;")

        operator fun get(id: Long) = select("SELECT * FROM COMMENTS WHERE ID = $id;") {
            getInt("COMMENT")
        }.singleOrNull()

    }

    object Labels : GitHubCommonDatabase() {

        init {
            create("CREATE TABLE LABELS (ID INTEGER PRIMARY KEY NOT NULL, NAME TEXT NOT NULL,COLOR CHAR(6) NOT NULL)")
        }

        fun create(label: Label) = with(label) {
            insert("INSERT INTO LABELS (ID,NAME,COLOR) VALUES ($id, '$name','$color');")
        }

        fun update(label: Label) = with(label) {
            update("UPDATE LABELS SET NAME = '$name',COLOR = '$color' WHERE ID = $id;")
        }

        fun delete(id: Long) = delete("DELETE FROM LABELS WHERE ID = $id;")

        operator fun get(id: Long) = select("SELECT * FROM LABELS WHERE ID = $id;") {
            Label(id, getString("NAME"), getString("COLOR"))
        }.singleOrNull()

    }

    object Issues : GitHubCommonDatabase() {

        init {
            create(
                "CREATE TABLE ISSUES (" +
                        "ID INTEGER PRIMARY KEY NOT NULL, " +
                        "TITLE TEXT NOT NULL," +
                        "NUMBER INTEGER NOT NULL," +
                        "LABELS TEXT NOT NULL," +
                        "CREATEDAT TEXT NOT NULL," +
                        "UPDATEDAT TEXT NOT NULL," +
                        "BODY TEXT NOT NULL)"
            )
        }

        fun create(issue: Issue) = with(issue) {
            insert(
                "INSERT INTO ISSUES (ID,TITLE,NUMBER,LABELS,CREATEDAT,UPDATEDAT,BODY) " +
                        "VALUES ($id, '$title','$number','${labels.toDatabase()}','$createdAt','$updatedAt','$body');"
            )
            GitHubDatabase.Comments.create(id, comments)
        }

        fun update(issue: Issue) = with(issue) {
            update(
                "UPDATE ISSUES SET TITLE = '$title'," +
                        "NUMBER = $number, " +
                        "LABELS = '${labels.toDatabase()}', " +
                        "CREATEDAT = '$createdAt', " +
                        "UPDATEDAT = '$updatedAt', " +
                        "BODY = '$body' " +
                        "WHERE ID = $id;"
            )
            GitHubDatabase.Comments.update(id, comments)
        }

        fun delete(id: Long) {
            delete("DELETE FROM ISSUES WHERE ID = $id;")
            GitHubDatabase.Comments.delete(id)
        }

        operator fun get(id: Long) = select("SELECT * FROM ISSUES WHERE ID = $id;") {
            val title = getString("TITLE")
            val number = getInt("NUMBER")
            val comments = GitHubDatabase.Comments[id] ?: throw Exception("Comments is not find.")
            val labels = getString("LABELS").toLabels()
            val createdAt = getString("CREATEDAT")
            val updateAt = getString("UPDATEDAT")
            val body = getString("BODY")
            Issue(id, title, number, comments, labels, createdAt, updateAt, body)
        }.singleOrNull()

    }
}
