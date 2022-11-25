package de.hoersendung.steffen.assignmentProblem.util.process

import de.hoersendung.steffen.assignmentProblem.configuration.OutputConfiguration
import org.slf4j.Logger
import org.springframework.stereotype.Service

@Service
class ShellRunImpl(
    private val outputConfiguration: OutputConfiguration,
    private val processRunner: ProcessRunner,
    private val logger: Logger
) : ShellRun {

    override fun execute(): String {
        return try {
            runCommand()
        } catch (e: Exception) {
            logger.error(e.message)
            return errorMessage()
        }
    }

    override fun isSolverConfigured(): Boolean {
        return COMMAND != ""
    }

    private fun runCommand() : String {
        logger.info("executing '$COMMAND -f ${outputConfiguration.directory}/assignmentProblem.zpl'")

        val result = processRunner.exec(arrayOf(COMMAND, "-f", "assignmentProblem.zpl"), outputConfiguration.directory)

        logger.info("finished external command")
        return result
    }

    private fun errorMessage() =
        "could not run command \"$COMMAND -f ${outputConfiguration.directory}/assignmentProblem.zpl\""

    companion object {
        var COMMAND = ""
    }
}
