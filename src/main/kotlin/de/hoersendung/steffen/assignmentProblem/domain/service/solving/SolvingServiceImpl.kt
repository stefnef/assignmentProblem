package de.hoersendung.steffen.assignmentProblem.domain.service.solving

import de.hoersendung.steffen.assignmentProblem.domain.model.entity.SolutionAssignment
import de.hoersendung.steffen.assignmentProblem.domain.service.solution.MIPSolver
import de.hoersendung.steffen.assignmentProblem.util.io.FileWriter
import org.slf4j.Logger
import org.springframework.stereotype.Service

@Service
class SolvingServiceImpl(
    private val fileWrite : FileWriter,
    private val mipSolver: MIPSolver,
    private val logger : Logger
) : SolvingService {

    override fun solve() {
        fileWrite.copyLinearProgramm()
        val isSolverConfigured = mipSolver.isSolverConfigured()
        if (isSolverConfigured) {
            solveAndReturnSolution()
        } else {
            logger.warn("No solver configured - no solution found!")
        }
    }

    private fun solveAndReturnSolution() {
        val solution = mipSolver.solve()
        checkSolution(solution)
    }

    private fun checkSolution(solution: List<SolutionAssignment>) {
        if (solution.isEmpty()) {
            logger.warn("Problem is infeasible - no solution found!")
        } else {
            fileWrite.writeSolution(solution)
        }
    }
}
