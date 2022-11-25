package de.hoersendung.steffen.assignmentProblem.util.process

import de.hoersendung.steffen.assignmentProblem.configuration.OutputConfiguration
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.given
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.slf4j.Logger

internal class ShellRunImplTest {

    private val logger : Logger = mock()
    private val outputDirectory = "some/directory"
    private val processRunner : ProcessRunner = mock()
    private val outputConfiguration = OutputConfiguration(directory = outputDirectory)

    private val runner = ShellRunImpl(outputConfiguration, processRunner, logger)

    @Test
    internal fun `should call command`() {
        ShellRunImpl.COMMAND = "scip"

        given(processRunner.exec(arrayOf("scip", "-f", "assignmentProblem.zpl"), "some/directory")).willReturn("data")

        val result = runner.execute()
        verify(logger).info("executing 'scip -f some/directory/assignmentProblem.zpl'")
        verify(logger).info("finished external command")
        assertThat(result).isEqualTo("data")
    }

    @Test
    internal fun `should propagate exception`() {
        ShellRunImpl.COMMAND = "cmd"
        val exception = IllegalArgumentException("fake")
        given(processRunner.exec(any(), any())).willThrow(exception)

        val msg = runner.execute()

        assertThat(msg).isEqualTo("could not run command \"cmd -f some/directory/assignmentProblem.zpl\"")
        verify(logger).error("fake")
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
