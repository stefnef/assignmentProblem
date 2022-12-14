package de.hoersendung.steffen.assignmentProblem.domain.service.solution

import de.hoersendung.steffen.assignmentProblem.domain.model.entity.SolutionAssignment
import de.hoersendung.steffen.assignmentProblem.domain.model.valueObject.PriorityValue
import de.hoersendung.steffen.assignmentProblem.domain.model.valueObject.Quartal
import de.hoersendung.steffen.assignmentProblem.domain.model.valueObject.StudentName
import de.hoersendung.steffen.assignmentProblem.domain.model.valueObject.SubjectName
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class SolutionParserImplTest{

    private val rawSolution = """
        Pre
        Gap                : 0.00 %

        primal solution (original space):
        =================================

        objective value:                                 1004
        x${"$"}Bond_Q1${"$"}Sportkurs_2                               1 	(obj:2)
        x${"$"}Mueller_Q3${"$"}Sportkurs_1                            1 	(obj:999)

        Statistics
        ==========
        
        And so on

    """

    private val rawInfeasible = """      
        primal solution (original space):
        =================================
        
        no solution available
        
        Statistics
        ==========
        
         
            """

    private val parser = SolutionParserImpl()

    @Test
    internal fun `should not parse a solution if there exsists no one`() {
        assertThat(parser.parse(rawInfeasible)).isEmpty()
    }

    @Test
    internal fun `should parse optimal solution`() {
        val solution : List<SolutionAssignment> = parser.parse(rawSolution)
        val bond = SolutionAssignment(
            StudentName("Bond"),
            Quartal("Q1"),
            SubjectName("Sportkurs_2"),
            PriorityValue(2)
        )
        val mueller = SolutionAssignment(
            StudentName("Mueller"),
            Quartal("Q3"),
            SubjectName("Sportkurs_1"),
            PriorityValue(999)
        )

        assertThat(solution.size).isEqualTo(2)
        assertThat(solution[0]).isEqualTo(bond)
        assertThat(solution[1]).isEqualTo(mueller)
    }

}
