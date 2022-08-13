package de.hoersendung.steffen.assignmentProblem

import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.slf4j.Logger
import org.springframework.boot.DefaultApplicationArguments

internal class AssignmentProblemApplicationTest {

    private val logger : Logger = mock(Logger::class.java)
    private val assignmentProblemApplication = AssignmentProblemApplication(logger)

    private val basePath = "src/test/files"

    @Test
    fun `it should accept a priority and capacity arguments`() {
        val args = DefaultApplicationArguments("--prio=${basePath}/priorities.csv", "--cap=${basePath}/capacities.csv")

        assignmentProblemApplication.run(args)

        verify(logger, never()).error(any())
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
        val args = DefaultApplicationArguments("--prio=${basePath}/priorities.csv", "--cap=never-existed.csv")

        assignmentProblemApplication.run(args)

        verify(logger).error("File \"never-existed.csv\" was not found")
    }

    private fun verifyThatUsageMessageWasCalled() {
        verify(logger).error("wrong or missing arguments")
        verify(logger).error("usage: --prio=\"<path to file of priorities>\" --cap=\"<path to file of capacities>\"")
        verify(logger).error("note that both files have to be in csv format")
    }
}
