package database

import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet

abstract class Database {

    abstract val sql: DatabaseSQL<*>

    private fun connection(block: Connection.() -> Unit) {
        Class.forName("org.sqlite.JDBC")
        DriverManager.getConnection("jdbc:sqlite:${sql.filePath}").use(block)
    }

    fun create(sql: String) {
        try {
            connection { createStatement().use { it.executeUpdate(sql) } }
        } catch (e: Exception) {

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

    fun <T> select(sql: String, block: ResultSet.() -> T) = ArrayList<T>().apply {
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

class DatabaseSQL<T>(private val clazz: Class<T>) {

    val name: String get() = clazz.simpleName

    val deleteAll: String get() = "DELETE from $name;"

    val selectAll: String get() = "SELECT * FROM $name;"

    val filePath :String get() =  "database/${clazz.simpleName}.db"

    val create: String
        get() = clazz.declaredFields.joinToString(",") { filed ->
            val fieldType = when (filed.type.simpleName) {
                "int", "long", "byte", "short" -> "INTEGER"
                "float", "double" -> "REAL"
                else -> "TEXT"
            }
            val fieldConstraint = if (filed.name == "id") "PRIMARY KEY" else "NOT NULL"
            "${filed.name} $fieldType $fieldConstraint"
        }.let {
            "create TABLE ${clazz.simpleName} ($it)"
        }

    fun insert(data: T): String {
        val tableName = clazz.simpleName
        val fields = clazz.declaredFields.joinToString(",") { it.name }
        val fieldValues = clazz.declaredFields.joinToString(",") {
            val accessFlag = it.isAccessible
            it.isAccessible = true
            val value = it.get(data)
            it.isAccessible = accessFlag
            "'$value'"
        }
        return "insert INTO $tableName ($fields) VALUES ($fieldValues);"
    }


}
