package utils

import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet

abstract class Database {

    abstract val fileName: String

    private fun connection(block: Connection.() -> Unit) {
        Class.forName("org.sqlite.JDBC")
        DriverManager.getConnection("jdbc:sqlite:${if (isWindows10) "" else "/"}home/program/database/$fileName.db")
            .use(block)
    }

    protected fun create(sql: String) {
        try {
            connection { createStatement().use { it.executeUpdate(sql) } }
        } catch (e: Exception) {

        }
    }

    protected fun insert(sql: String) = connection {
        autoCommit = false
        createStatement().use {
            it.executeUpdate(sql)
            commit()
        }
    }

    protected fun update(sql: String) = insert(sql)

    protected fun delete(sql: String) = insert(sql)

    protected fun <T> select(sql: String, block: ResultSet.() -> T) = ArrayList<T>().apply {
        connection {
            autoCommit = false
            createStatement().use { statement ->
                statement.executeQuery(sql).use {
                    while (it.next()) {
                        add(block(it))
                    }
                }
            }
        }
    }.toList()
}