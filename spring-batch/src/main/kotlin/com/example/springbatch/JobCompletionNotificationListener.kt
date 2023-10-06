package com.example.springbatch

import org.slf4j.LoggerFactory
import org.springframework.batch.core.BatchStatus
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.JobExecutionListener
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component
import java.sql.ResultSet
import java.util.function.Consumer

@Component
class JobCompletionNotificationListener(val jdbcTemplate: JdbcTemplate): JobExecutionListener {
    val log = LoggerFactory.getLogger(JobCompletionNotificationListener::class.java)

    override fun afterJob(jobExecution: JobExecution) {
        if (jobExecution.status == BatchStatus.COMPLETED) {
            log.info("!!! Job Finished! Time to verify the results")

            jdbcTemplate.query(
                "SELECT first_name, last_name FROM people"
            ) { rs: ResultSet, row: Int ->
                Person(
                    rs.getString(1),
                    rs.getString(2)
                )
            }.forEach(Consumer { person: Person? ->
                log.info(
                    "Found <{{}}> in the database.",
                    person
                )
            })
        }
    }
}
