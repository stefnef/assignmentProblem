package de.hoersendung.steffen.assignmentProblem.service.solving

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class SolutionParserImplTest{

    private val rawOutput = """
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

    private val parser = SolutionParserImpl()

    @Test
    internal fun `should parse optimal solution`() {
        val solution = parser.parse(rawOutput)

        assertThat(solution).contains("Bond")
        assertThat(solution).doesNotContain("Gap")
        assertThat(solution).doesNotContain("Statistics")
        assertThat(solution).hasLineCount(3)
    }
}
