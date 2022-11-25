package de.hoersendung.steffen.assignmentProblem.service

import de.hoersendung.steffen.assignmentProblem.domain.service.priority.PriorityService
import de.hoersendung.steffen.assignmentProblem.domain.service.solving.SolvingService
import de.hoersendung.steffen.assignmentProblem.domain.service.subject.SubjectService
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import java.io.File

internal class AssignmentProblemApplicationServiceImplTest {

    private val capacities = File("capacities.csv")
    private val priorities = File("priorities.csv")

    private val subjectService : SubjectService = mock()
    private val priorityService : PriorityService = mock()
    private val solvingService : SolvingService = mock()

    private val assignmentProblemApplicationServiceImpl = AssignmentProblemApplicationServiceImpl(subjectService,
        priorityService, solvingService)

    @Test
    internal fun `it should load subjects and priorities`() {
        assignmentProblemApplicationServiceImpl.solveProblem(priorities, capacities)

        verify(priorityService).loadPriorities(priorities)
        verify(subjectService).loadCapacities(capacities)
        verify(solvingService).solve()
    }
}
