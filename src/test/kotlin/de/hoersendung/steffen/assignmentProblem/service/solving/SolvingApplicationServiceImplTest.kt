package de.hoersendung.steffen.assignmentProblem.service.solving

import de.hoersendung.steffen.assignmentProblem.domain.entity.SolutionAssignment
import de.hoersendung.steffen.assignmentProblem.domain.valueObject.PriorityValue
import de.hoersendung.steffen.assignmentProblem.domain.valueObject.Quartal
import de.hoersendung.steffen.assignmentProblem.domain.valueObject.StudentName
import de.hoersendung.steffen.assignmentProblem.domain.valueObject.SubjectName
import de.hoersendung.steffen.assignmentProblem.service.FileWriter
import org.junit.jupiter.api.Test
import org.mockito.kotlin.given
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

internal class SolvingApplicationServiceImplTest {

    private val fileWrite : FileWriter = mock()
    private val mipSolver : MIPSolver = mock()

    private val solvingApplicationService = SolvingApplicationServiceImpl(fileWrite, mipSolver)

    @Test
    internal fun `should add zpl file from classpath`() {
        val solution = listOf(SolutionAssignment(
            StudentName("Alice"),
            Quartal("Q1"),
            SubjectName("Bouldern"),
            PriorityValue(1)
        ))

        given(mipSolver.solve()).willReturn(solution)

        solvingApplicationService.solve()

        verify(fileWrite).copyLinearProgramm()
        verify(mipSolver).solve()
        verify(fileWrite).writeSolution(solution)
    }
}
