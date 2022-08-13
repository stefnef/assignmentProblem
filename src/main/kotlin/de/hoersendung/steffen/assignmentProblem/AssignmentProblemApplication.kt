package de.hoersendung.steffen.assignmentProblem

import org.slf4j.Logger
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.io.File

@SpringBootApplication
class AssignmentProblemApplication(private val logger : Logger) : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        if(!isArgumentListValid(args)) {
            return
        }

        getFile(args!!, "prio") ?: return
        getFile(args, "cap")
    }

    private fun getFile(args: ApplicationArguments, optionName: String) : File? {
        val fileName = args.getOptionValues(optionName).single()
        val file = File(fileName)
        if (!file.exists()) {
            logger.error("File \"${fileName}\" was not found")
            return null
        }
        return file
    }

    private fun isArgumentListValid(args: ApplicationArguments?) : Boolean {
        if (args == null) {
            logUsageText()
            return false
        }

        if (args.optionNames.size != 2) {
            logUsageText()
            return false
        }

        if (!args.optionNames.contains("prio") || !args.optionNames.contains("cap")) {
            logUsageText()
            return false
        }
        return true
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
