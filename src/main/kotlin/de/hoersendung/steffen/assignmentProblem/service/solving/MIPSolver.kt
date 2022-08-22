package de.hoersendung.steffen.assignmentProblem.service.solving

import de.hoersendung.steffen.assignmentProblem.domain.entity.SolutionAssignment

interface MIPSolver {
    fun solve() : List<SolutionAssignment>

}
