package de.hoersendung.steffen.assignmentProblem.service.solving

import de.hoersendung.steffen.assignmentProblem.configuration.OutputConfiguration
import org.slf4j.Logger
import org.springframework.stereotype.Service
import ru.iopump.koproc.startProcess

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
        val rawOutput = "$COMMAND -f ${outputConfiguration.directory}\\assignmentProblem.zpl".startProcess { timeoutSec = 60 }

        var output: String
        var err: String
        rawOutput.use {
            output = rawOutput.readAvailableOut
            err = rawOutput.readAvailableErrOut
        }

        logger.info("finished external command")
        if(err.isNotBlank()) {
            throw RuntimeException(err)
        }
        return output
    }

    private fun errorMessage(e: RuntimeException) =
        e.message ?: "could not run command \"$COMMAND -f ${outputConfiguration.directory}/assignmentProblem.zpl\""

    companion object {
        var COMMAND = ""
    }
}
