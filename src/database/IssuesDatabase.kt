package database

import bean.Issue

object IssuesDatabase : Database() {

    override val sql = DatabaseSQL(Issue::class.java)

}