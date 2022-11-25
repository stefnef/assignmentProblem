package de.hoersendung.steffen.assignmentProblem.service

import de.hoersendung.steffen.assignmentProblem.domain.service.priority.PriorityService
import de.hoersendung.steffen.assignmentProblem.domain.service.solving.SolvingService
import de.hoersendung.steffen.assignmentProblem.domain.service.subject.SubjectService
import org.springframework.stereotype.Service
import java.io.File

@Service
class AssignmentProblemApplicationServiceImpl(
    private val subjectService: SubjectService,
    private val priorityService : PriorityService,
    private val solvingService : SolvingService
) : AssignmentProblemApplicationService {

    override fun solveProblem(priorities: File, capacities: File) {
        subjectService.loadCapacities(capacities)
        priorityService.loadPriorities(priorities)
        solvingService.solve()
    }
}
