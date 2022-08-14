package de.hoersendung.steffen.assignmentProblem.service

import de.hoersendung.steffen.assignmentProblem.service.priority.PriorityApplicationService
import de.hoersendung.steffen.assignmentProblem.service.subject.SubjectApplicationService
import org.springframework.stereotype.Service
import java.io.File

@Service
class AssignmentProblemApplicationServiceImpl(
    private val subjectApplicationService: SubjectApplicationService,
    private val priorityApplicationService : PriorityApplicationService
) : AssignmentProblemApplicationService {

    override fun solveProblem(priorities: File, capacities: File) {
        subjectApplicationService.loadCapacities(capacities)
        priorityApplicationService.loadPriorities(priorities)
    }
}
