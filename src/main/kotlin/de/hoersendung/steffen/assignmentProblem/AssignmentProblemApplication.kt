package de.hoersendung.steffen.assignmentProblem

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import de.hoersendung.steffen.assignmentProblem.configuration.ConfigFile
import de.hoersendung.steffen.assignmentProblem.configuration.SolverConfiguration
import de.hoersendung.steffen.assignmentProblem.service.AssignmentProblemApplicationService
import org.slf4j.Logger
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import java.io.File

@SpringBootApplication
@ConfigurationPropertiesScan
class AssignmentProblemApplication(
    private val logger : Logger,
    private val configFile: ConfigFile,
    private val applicationService: AssignmentProblemApplicationService) : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        val configuration = readConfiguration()

        val priorities = getFile(configuration.priorities) ?: return
        val capacities = getFile(configuration.capacities) ?: return

        try {
            applicationService.solveProblem(priorities, capacities)
        } catch (e: Exception) {
            logger.error(e.message)
        }

    }

    private fun getFile(fileName: String) : File? {
        val file = File(fileName)
        if (!file.exists()) {
            logger.error("File \"${fileName}\" was not found")
            return null
        }
        return file
    }

    fun readConfiguration() : SolverConfiguration {
        return jacksonObjectMapper().readValue(File(configFile.configFile), SolverConfiguration::class.java)
    }
}

fun main(args: Array<String>) {
    runApplication<AssignmentProblemApplication>(*args)
}
