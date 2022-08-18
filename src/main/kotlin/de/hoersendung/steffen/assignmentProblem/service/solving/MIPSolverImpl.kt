package de.hoersendung.steffen.assignmentProblem.service.solving

import org.springframework.stereotype.Service

@Service
class MIPSolverImpl(
    private val shellRun : ShellRun,
    private val solutionParser : SolutionParser
) : MIPSolver {

    override fun solve() : String {
        val rawOutput = shellRun.execute()
        return solutionParser.parse(rawOutput)
    }
}
