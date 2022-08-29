package de.hoersendung.steffen.assignmentProblem.service.solving

import com.lordcodes.turtle.shellRun
import de.hoersendung.steffen.assignmentProblem.configuration.OutputConfiguration
import org.slf4j.Logger
import org.springframework.stereotype.Service
import java.io.File

@Service
class ShellRunImpl(
    private val outputConfiguration : OutputConfiguration,
    private val logger : Logger
) : ShellRun {

    override fun execute(): String {
        return try {
            runCommand()
        } catch (e: RuntimeException) {
            logger.error(e.message)
            return errorMessage(e)
        }
    }

    override fun isSolverConfigured(): Boolean {
        return COMMAND != ""
    }

    private fun runCommand() : String {
        logger.info("executing '$COMMAND -f ${outputConfiguration.directory}/assignmentProblem.zpl'")
        val rawOutput = shellRun(COMMAND, listOf("-f", "assignmentProblem.zpl"), File(outputConfiguration.directory))
        logger.info("finished external command")
        return rawOutput
    }

    private fun errorMessage(e: RuntimeException) =
        e.message ?: "could not run command \"$COMMAND -f ${outputConfiguration.directory}/assignmentProblem.zpl\""

    companion object {
        var COMMAND = ""
    }
}
