package de.hoersendung.steffen.assignmentProblem.service.solving

import de.hoersendung.steffen.assignmentProblem.domain.entity.SolutionAssignment
import org.springframework.stereotype.Service

@Service
class MIPSolverImpl(
    private val shellRun : ShellRun,
    private val solutionParser : SolutionParser
) : MIPSolver {

    override fun solve(): List<SolutionAssignment> {
        val rawOutput = shellRun.execute()
        return solutionParser.parse(rawOutput)
    }
}
