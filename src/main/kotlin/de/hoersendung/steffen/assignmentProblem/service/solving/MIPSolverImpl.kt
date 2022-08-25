package de.hoersendung.steffen.assignmentProblem.service.solving

import de.hoersendung.steffen.assignmentProblem.domain.entity.SolutionAssignment
import org.springframework.stereotype.Service

@Service
class MIPSolverImpl(
    private val shellRun : ShellRun,
    private val solutionParser : SolutionParser
) : MIPSolver {

    override fun solve(): List<SolutionAssignment> {
        return if (shellRun.isSolverConfigured()) {
            solveAndReturnSolution()
        } else {
            emptyList()
        }
    }

    override fun isSolverConfigured(): Boolean {
        return shellRun.isSolverConfigured()
    }

    private fun solveAndReturnSolution(): List<SolutionAssignment> {
        val rawOutput = shellRun.execute()
        return solutionParser.parse(rawOutput)
    }
}
