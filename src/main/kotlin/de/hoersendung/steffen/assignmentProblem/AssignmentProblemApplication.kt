package de.hoersendung.steffen.assignmentProblem

import org.slf4j.Logger
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AssignmentProblemApplication(private val logger : Logger) : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        checkUsage(args)
    }

    private fun checkUsage(args: ApplicationArguments?) {
        if (args == null) {
            logUsageText()
            return
        }

        if (args.optionNames.size != 2) {
            logUsageText()
            return
        }

        if (!args.optionNames.contains("prio") || !args.optionNames.contains("cap")) {
            logUsageText()
            return
        }
    }

    private fun logUsageText() {
        logger.error("wrong or missing arguments")
        logger.error("usage: --prio=\"<path to file of priorities>\" --cap=\"<path to file of capacities>\"")
        logger.error("note that both files have to be in csv format")
    }
}

fun main(args: Array<String>) {
    runApplication<AssignmentProblemApplication>(*args)
}
