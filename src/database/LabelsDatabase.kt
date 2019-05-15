package database

import utils.databasePath
import java.sql.Connection
import java.sql.DriverManager

data class Label(val id: String, val name: String, val color: String)

val Label.INSERT get() = "INSERT INTO LABELS (ID,NAME,COLOR) VALUES ('$id','$name','$color');"

object LabelsDatabase {

    private fun connection(block: Connection.() -> Unit) {
        Class.forName("org.sqlite.JDBC")
        DriverManager.getConnection("jdbc:sqlite:${databasePath("labels")}").use(block)
    }

    init {
        try {
            connection {
                createStatement().use {
                    it.executeUpdate("CREATE TABLE LABELS (ID TEXT PRIMARY KEY,NAME TEXT NOT NULL,COLOR TEXT NOT NULL)")
                }
            }
        } catch (e: Exception) {
            //一般都是已经创建了
        }
    }

    private fun add(labels: List<Label>) {
        connection {
            autoCommit = false
            createStatement().use { statement ->
                labels.forEach { statement.executeUpdate(it.INSERT) }
                commit()
            }
        }
    }

    private fun deleteAll() {
        connection {
            autoCommit = false
            createStatement().use {
                it.executeUpdate("DELETE from LABELS;")
                commit()
            }
        }
    }

    fun update(labels: List<Label>) {
        deleteAll()
        add(labels)
    }

    fun getAll(): ArrayList<Label> {
        val r = ArrayList<Label>()
        connection {
            autoCommit = false
            createStatement().use { statement ->
                statement.executeQuery("SELECT * FROM LABELS;").use {
                    while (it.next()) {
                        val id = it.getString("ID")
                        val name = it.getString("NAME")
                        val color = it.getString("COLOR")
                        r.add(Label(id, name, color))
                    }
                }
            }
        }
        return r
    }
}