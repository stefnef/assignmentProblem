package de.hoersendung.steffen.assignmentProblem.domain.service.solution

import de.hoersendung.steffen.assignmentProblem.domain.model.entity.SolutionAssignment

interface MIPSolver {
    fun solve() : List<SolutionAssignment>
    fun isSolverConfigured(): Boolean

}
