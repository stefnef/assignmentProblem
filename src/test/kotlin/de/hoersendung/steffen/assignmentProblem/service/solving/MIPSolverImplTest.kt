package de.hoersendung.steffen.assignmentProblem.service.solving

import de.hoersendung.steffen.assignmentProblem.domain.entity.SolutionAssignment
import de.hoersendung.steffen.assignmentProblem.domain.valueObject.PriorityValue
import de.hoersendung.steffen.assignmentProblem.domain.valueObject.Quartal
import de.hoersendung.steffen.assignmentProblem.domain.valueObject.StudentName
import de.hoersendung.steffen.assignmentProblem.domain.valueObject.SubjectName
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.kotlin.given
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

internal class MIPSolverImplTest {

    private val shellRun : ShellRun = mock()
    private val solutionParser : SolutionParser = mock()
    private val mipSolver = MIPSolverImpl(shellRun, solutionParser)

    private val rawOutput = "raw output of solver"

    @Test
    internal fun `it should solve`() {
        val solution = listOf(SolutionAssignment(
            StudentName("Peter"),
            Quartal("Q1"), SubjectName("Sport"),
            PriorityValue(1)
        ))
        given(shellRun.execute()).willReturn(rawOutput)
        given(solutionParser.parse(rawOutput)).willReturn(solution)

        val foundSolution = mipSolver.solve()
        assertThat(foundSolution).isEqualTo(solution)

        verify(shellRun).execute()
    }
}
