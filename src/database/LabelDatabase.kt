package database

import bean.Label

object LabelDatabase : Database() {

    override val sql = DatabaseSQL(Label::class.java)

    init {
        create(sql.create)
    }

    fun set(labels: List<Label>) {
        delete(sql.deleteAll)
        labels.forEach { insert(sql.insert(it)) }
    }

    fun get() = select(sql.selectAll) {
        val id = getLong("id")
        val name = getString("name")
        val color = getString("color")
        Label(id, name, color)
    }
}