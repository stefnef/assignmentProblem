package de.hoersendung.steffen.assignmentProblem.service.solving

import de.hoersendung.steffen.assignmentProblem.configuration.OutputConfiguration
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class ShellRunImplTest {

    private val outputDirectory = "some/directory"
    private val outputConfiguration = OutputConfiguration(directory = outputDirectory)

    private val runner = ShellRunImpl(outputConfiguration)

    @Test
    internal fun `should call command`() {
        ShellRunImpl.COMMAND = "ls"

        val rawOutput = runner.execute()
        assertThat(rawOutput).contains("some/directory/assignmentProblem.zpl")
    }
}
