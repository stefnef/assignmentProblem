package de.hoersendung.steffen.assignmentProblem.domain.service.solution

import de.hoersendung.steffen.assignmentProblem.domain.model.entity.SolutionAssignment

interface SolutionParser {

    fun parse(solutionText: String): List<SolutionAssignment>

}
