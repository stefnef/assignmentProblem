package de.hoersendung.steffen.assignmentProblem

import de.hoersendung.steffen.assignmentProblem.service.AssignmentProblemApplicationService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import org.slf4j.Logger
import org.springframework.boot.DefaultApplicationArguments
import java.io.File

internal class AssignmentProblemApplicationTest {

    private val logger : Logger = mock()
    private val applicationService : AssignmentProblemApplicationService = mock()
    private val assignmentProblemApplication = AssignmentProblemApplication(logger, applicationService)

    private val basePath = "src/test/files"
    private val prioritiesPath = "$basePath/priority"
    private val capacityPath = "$basePath/capacity"

    @Test
    fun `it should accept a priority and capacity arguments`() {
        val args = DefaultApplicationArguments("--prio=${prioritiesPath}/priorities.csv", "--cap=${capacityPath}/capacities.csv")

        assignmentProblemApplication.run(args)

        verify(logger, never()).error(any())
        argumentCaptor<File>().apply {
            verify(applicationService).solveProblem(capture(), capture())
            assertThat(firstValue.name).isEqualTo("priorities.csv")
            assertThat(secondValue.name).isEqualTo("capacities.csv")
        }
    }

    @Test
    fun `it should log usage if priority argument is missing`() {
        val args = DefaultApplicationArguments("--neverEver=3", "--cap=\"capacities.csv\"")
        assignmentProblemApplication.run(args)
        verifyThatUsageMessageWasCalled()
    }

    @Test
    fun `it should log usage if subject argument is missing`() {
        val args = DefaultApplicationArguments("--neverEver=3", "--prio=\"prio.csv\"")
        assignmentProblemApplication.run(args)
        verifyThatUsageMessageWasCalled()
    }

    @Test
    internal fun `it should not accept empty argument list`() {
        assignmentProblemApplication.run(null)
        verifyThatUsageMessageWasCalled()
    }

    @Test
    internal fun `should handle wrong number of arguments`() {
        val args = DefaultApplicationArguments("--neverEver=someThing")
        assignmentProblemApplication.run(args)
        verifyThatUsageMessageWasCalled()
    }

    @Test
    internal fun `should log that priorities file does not exist`() {
        val args = DefaultApplicationArguments("--prio=i-do-not-exist.csv", "--cap=capacities.csv")

        assignmentProblemApplication.run(args)

        verify(logger).error("File \"i-do-not-exist.csv\" was not found")
        verify(logger, never()).error("File \"capacities.csv\" was not found")
    }

    @Test
    internal fun `should log that capacities file does not exist`() {
        val args = DefaultApplicationArguments("--prio=${prioritiesPath}/priorities.csv", "--cap=never-existed.csv")

        assignmentProblemApplication.run(args)

        verify(logger).error("File \"never-existed.csv\" was not found")
    }

    @Test
    internal fun `it should propagate Exceptions`() {
        val args = DefaultApplicationArguments("--prio=${prioritiesPath}/priorities.csv", "--cap=${capacityPath}/capacities.csv")
        given(applicationService.solveProblem(any(), any())).willThrow(IllegalArgumentException("Fake"))

        assignmentProblemApplication.run(args)

        verify(logger).error("Fake")
    }

    private fun verifyThatUsageMessageWasCalled() {
        verify(logger).error("wrong or missing arguments")
        verify(logger).error("usage: --prio=\"<path to file of priorities>\" --cap=\"<path to file of capacities>\"")
        verify(logger).error("note that both files have to be in csv format")
        verify(applicationService, never()).solveProblem(any(), any())
    }
}
