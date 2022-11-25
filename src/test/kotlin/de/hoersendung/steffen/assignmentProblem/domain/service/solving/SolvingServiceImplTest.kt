package de.hoersendung.steffen.assignmentProblem.domain.service.solving

import de.hoersendung.steffen.assignmentProblem.domain.model.entity.SolutionAssignment
import de.hoersendung.steffen.assignmentProblem.domain.model.valueObject.PriorityValue
import de.hoersendung.steffen.assignmentProblem.domain.model.valueObject.Quartal
import de.hoersendung.steffen.assignmentProblem.domain.model.valueObject.StudentName
import de.hoersendung.steffen.assignmentProblem.domain.model.valueObject.SubjectName
import de.hoersendung.steffen.assignmentProblem.domain.service.solution.MIPSolver
import de.hoersendung.steffen.assignmentProblem.util.io.FileWriter
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import org.slf4j.Logger

internal class SolvingServiceImplTest {

    private val fileWrite : FileWriter = mock()
    private val mipSolver : MIPSolver = mock()
    private val logger : Logger = mock()

    private val solvingApplicationService = SolvingServiceImpl(fileWrite, mipSolver, logger)

    @Test
    internal fun `should add zpl file from classpath`() {
        given(mipSolver.isSolverConfigured()).willReturn(true)
        val solution = listOf(
            SolutionAssignment(
            StudentName("Alice"),
            Quartal("Q1"),
            SubjectName("Bouldern"),
            PriorityValue(1)
        )
        )

        given(mipSolver.solve()).willReturn(solution)

        solvingApplicationService.solve()

        verify(fileWrite).copyLinearProgramm()
        verify(mipSolver).solve()
        verify(fileWrite).writeSolution(solution)
    }

    @Test
    internal fun `should not write file if there was no solution found`() {
        given(mipSolver.isSolverConfigured()).willReturn(true)
        given(mipSolver.solve()).willReturn(emptyList())

        solvingApplicationService.solve()

        verify(fileWrite, never()).writeSolution(any())
        verify(logger).warn("Problem is infeasible - no solution found!")
    }

    @Test
    internal fun `should log if no solver was configured`() {
        given(mipSolver.isSolverConfigured()).willReturn(false)

        solvingApplicationService.solve()

        verify(fileWrite, never()).writeSolution(any())
        verify(logger).warn("No solver configured - no solution found!")
    }
}
