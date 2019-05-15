package database

import bean.Issues

object IssuesDatabase : Database() {

    override val sql = DatabaseSQL(Issues::class.java)

}