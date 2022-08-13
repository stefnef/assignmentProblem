package de.hoersendung.steffen.assignmentProblem.service

import java.io.File

interface AssignmentProblemApplicationService {
    fun solveProblem(priorities: File, capacities: File)

}
