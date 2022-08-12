package de.hoersendung.steffen.assignmentProblem

import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.slf4j.Logger
import org.springframework.boot.DefaultApplicationArguments

internal class AssignmentProblemApplicationTest {
    private val logger : Logger = Mockito.mock(Logger::class.java)
    private val assignmentProblemApplication = AssignmentProblemApplication(logger)

    @Test
    fun `it should accept a priority and capacity arguments`() {
        val args = DefaultApplicationArguments("--prio=\"prio.csv\"", "--cap=\"subjects.csv\"")
        assignmentProblemApplication.run(args)
        Mockito.verify(logger, Mockito.never()).error(Mockito.any())
    }

    @Test
    fun `it should log usage if priority argument is missing`() {
        val args = DefaultApplicationArguments("--neverEver=3", "--cap=\"subjects.csv\"")
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

    private fun verifyThatUsageMessageWasCalled() {
        Mockito.verify(logger).error("wrong or missing arguments")
        Mockito.verify(logger).error("usage: --prio=\"<path to file of priorities>\" --cap=\"<path to file of capacities>\"")
        Mockito.verify(logger).error("note that both files have to be in csv format")
    }
}
