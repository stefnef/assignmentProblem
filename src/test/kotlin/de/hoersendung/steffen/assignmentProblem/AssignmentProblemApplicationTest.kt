package de.hoersendung.steffen.assignmentProblem

import de.hoersendung.steffen.assignmentProblem.configuration.ConfigFile
import de.hoersendung.steffen.assignmentProblem.service.AssignmentProblemApplicationService
import de.hoersendung.steffen.assignmentProblem.util.process.ShellRunImpl
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import org.slf4j.Logger
import org.springframework.boot.DefaultApplicationArguments
import java.io.File

internal class AssignmentProblemApplicationTest {

    private val logger : Logger = mock()
    private val configFile = ConfigFile(configFile = "")
    private val applicationService : AssignmentProblemApplicationService = mock()
    private val assignmentProblemApplication = AssignmentProblemApplication(logger, configFile, applicationService)

    private val basePath = "src/test/files"
    private val configPath = "$basePath/configuration"

    @Test
    internal fun `it should throw exception if priority file does not exist`() {
        configFile.configFile = "$configPath/configWrongPriority.json"
        assignmentProblemApplication.run(DefaultApplicationArguments())

        verify(logger).error("File \"src/test/files/priority/i-do-not-exist.csv\" was not found")
        verify(logger, never()).error("File \"src/test/files/capacity/capacities.csv\" was not found")
        verify(applicationService, never()).solveProblem(any(), any())
    }

    @Test
    internal fun `it should throw exception if capacity file does not exist`() {
        configFile.configFile = "$configPath/configWrongCapacity.json"
        assignmentProblemApplication.run(DefaultApplicationArguments())

        verify(logger).error("File \"src/test/files/capacity/i-do-not-exist.csv\" was not found")
        verify(logger, never()).error("File \"src/test/files/priority/priorities.csv\" was not found")
        verify(applicationService, never()).solveProblem(any(), any())
    }

    @Test
    internal fun `should set solver configuration`() {
        configFile.configFile = "$configPath/config.json"
        assignmentProblemApplication.run(DefaultApplicationArguments())

        assertThat(ShellRunImpl.COMMAND).isEqualTo("scip")
    }

    @Test
    internal fun `should log if configFile was not found`() {
        configFile.configFile = "i-do-not-exist"
        assignmentProblemApplication.run(DefaultApplicationArguments())
        verify(logger).error("could not find config file \"i-do-not-exist\"")
    }

    @Test
    internal fun `it should start solving`() {
        configFile.configFile = "$configPath/config.json"
        assignmentProblemApplication.run(DefaultApplicationArguments())

        argumentCaptor<File>().apply {
            verify(applicationService).solveProblem(capture(), capture())
            assertThat(firstValue.name).isEqualTo("priorities.csv")
            assertThat(secondValue.name).isEqualTo("capacities.csv")
        }
    }
}
