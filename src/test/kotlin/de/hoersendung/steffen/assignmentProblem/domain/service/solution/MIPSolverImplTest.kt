package de.hoersendung.steffen.assignmentProblem.domain.service.solution

import de.hoersendung.steffen.assignmentProblem.domain.model.entity.SolutionAssignment
import de.hoersendung.steffen.assignmentProblem.domain.model.valueObject.PriorityValue
import de.hoersendung.steffen.assignmentProblem.domain.model.valueObject.Quartal
import de.hoersendung.steffen.assignmentProblem.domain.model.valueObject.StudentName
import de.hoersendung.steffen.assignmentProblem.domain.model.valueObject.SubjectName
import de.hoersendung.steffen.assignmentProblem.util.process.ShellRun
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.kotlin.given
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify

internal class MIPSolverImplTest {

    private val shellRun : ShellRun = mock()
    private val solutionParser : SolutionParser = mock()
    private val mipSolver = MIPSolverImpl(shellRun, solutionParser)

    private val rawOutput = "raw output of solver"

    @Test
    internal fun `it should solve`() {
        val solution = listOf(
            SolutionAssignment(
            StudentName("Peter"),
            Quartal("Q1"), SubjectName("Sport"),
            PriorityValue(1)
        )
        )
        given(shellRun.isSolverConfigured()).willReturn(true)
        given(shellRun.execute()).willReturn(rawOutput)
        given(solutionParser.parse(rawOutput)).willReturn(solution)

        val foundSolution = mipSolver.solve()
        assertThat(foundSolution).isEqualTo(solution)

        verify(shellRun).execute()
    }

    @Test
    internal fun `it should not solve if solver is not configured`() {
        given(shellRun.isSolverConfigured()).willReturn(false)

        mipSolver.solve()

        verify(shellRun, never()).execute()
    }

    @Test
    internal fun `it should return true if solver was configured`() {
        given(shellRun.isSolverConfigured()).willReturn(true)

        assertThat(mipSolver.isSolverConfigured()).isTrue
    }

    @Test
    internal fun `it should return false if solver was not configured`() {
        given(shellRun.isSolverConfigured()).willReturn(false)

        assertThat(mipSolver.isSolverConfigured()).isFalse
    }
}
