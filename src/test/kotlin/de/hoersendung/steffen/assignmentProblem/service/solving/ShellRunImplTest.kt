package de.hoersendung.steffen.assignmentProblem.service.solving

import de.hoersendung.steffen.assignmentProblem.configuration.OutputConfiguration
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.slf4j.Logger

internal class ShellRunImplTest {

    private val logger : Logger = mock()
    private val outputDirectory = "some/directory"
    private val outputConfiguration = OutputConfiguration(directory = outputDirectory)

    private val runner = ShellRunImpl(outputConfiguration, logger)

    @Test
    internal fun `should call command`() {
        ShellRunImpl.COMMAND = "ls"

        runner.execute()
        verify(logger).info("executing 'ls -f some/directory/assignmentProblem.zpl'")
    }

    @Test
    internal fun `it should return false if no solver was configured`() {
        ShellRunImpl.COMMAND = ""

        assertThat(runner.isSolverConfigured()).isFalse
    }

    @Test
    internal fun `it should return true if a solver was configured`() {
        ShellRunImpl.COMMAND = "something"

        assertThat(runner.isSolverConfigured()).isTrue
    }
}
