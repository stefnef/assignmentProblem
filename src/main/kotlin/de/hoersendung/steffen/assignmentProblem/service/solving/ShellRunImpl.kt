package de.hoersendung.steffen.assignmentProblem.service.solving

import com.lordcodes.turtle.shellRun
import de.hoersendung.steffen.assignmentProblem.configuration.OutputConfiguration
import org.springframework.stereotype.Service

@Service
class ShellRunImpl(
    private val outputConfiguration : OutputConfiguration
) : ShellRun {

    override fun execute(): String {
        return try {
            runCommand()
        } catch (e: RuntimeException) {
            return errorMessage(e)
        }
    }

    override fun isSolverConfigured(): Boolean {
        return COMMAND != ""
    }

    private fun runCommand() = shellRun(COMMAND, listOf("-f", "${outputConfiguration.directory}/assignmentProblem.zpl"))

    private fun errorMessage(e: RuntimeException) =
        e.message ?: "could not run command \"$COMMAND -f ${outputConfiguration.directory}/assignmentProblem.zpl\""

    companion object {
        var COMMAND = ""
    }
}
