package database

import utils.println
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet

inline fun <reified T> databaseCreate(): String {
    val clazz = T::class.java
    return clazz.declaredFields.joinToString(",") { filed ->
        val fieldType = if (filed.type.simpleName == "int") "INTEGER" else "TEXT"
        val fieldConstraint = if (filed.name == "id") "PRIMARY KEY" else "NOT NULL"
        "${filed.name} $fieldType $fieldConstraint"
    }.let {
        "CREATE TABLE ${clazz.simpleName} ($it)"
    }
}

inline fun <reified T> databaseInsert(data: T): String {
    val clazz = T::class.java
    val tableName = clazz.simpleName
    val fields = clazz.declaredFields.joinToString(",") { it.name }
    val fieldValues = clazz.declaredFields.joinToString(",") {
        val accessFlag = it.isAccessible
        it.isAccessible = true
        val value = it.get(data)
        it.isAccessible = accessFlag
        "'$value'"
    }
    return "INSERT INTO $tableName ($fields) VALUES ($fieldValues);"
}

abstract class Database {

    abstract val filePath: String

    private fun connection(block: Connection.() -> Unit) {
        Class.forName("org.sqlite.JDBC")
        DriverManager.getConnection("jdbc:sqlite:$filePath").use(block)
    }

    fun create(sql: String) {
        try {
            connection { createStatement().use { it.executeUpdate(sql) } }
        } catch (e: Exception) {
            e.message?.println()
        }
    }

    fun insert(sql: String) = connection {
        autoCommit = false
        createStatement().use {
            it.executeUpdate(sql)
            commit()
        }
    }

    fun update(sql: String) = insert(sql)

    fun delete(sql: String) = insert(sql)

    fun select(sql: String, block: ResultSet.() -> Unit) = connection {
        autoCommit = false
        createStatement().use { statement ->
            statement.executeQuery(sql).use {
                while (it.next()) {
                    block(it)
                }
            }
        }
    }

}

