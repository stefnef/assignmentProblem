package de.hoersendung.steffen.assignmentProblem.service

import org.springframework.stereotype.Service
import java.io.File

@Service
class AssignmentProblemApplicationServiceImpl(
    private val subjectApplicationService: SubjectApplicationService) : AssignmentProblemApplicationService {

    override fun solveProblem(priorities: File, capacities: File) {
        subjectApplicationService.loadCapacities(capacities)
    }
}
