package de.hoersendung.steffen.assignmentProblem.service.solving

import de.hoersendung.steffen.assignmentProblem.domain.entity.SolutionAssignment

interface SolutionParser {

    fun parse(solutionText: String): List<SolutionAssignment>

}
