package de.hoersendung.steffen.assignmentProblem.service

import de.hoersendung.steffen.assignmentProblem.service.priority.PriorityApplicationService
import de.hoersendung.steffen.assignmentProblem.service.subject.SubjectApplicationService
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import java.io.File

internal class AssignmentProblemApplicationServiceImplTest {

    private val capacities = File("capacities.csv")
    private val priorities = File("priorities.csv")

    private val subjectApplicationService : SubjectApplicationService = mock()
    private val priorityApplicationService : PriorityApplicationService = mock()
    private val assignmentProblemApplicationServiceImpl = AssignmentProblemApplicationServiceImpl(subjectApplicationService,
        priorityApplicationService)

    @Test
    internal fun `it should load subjects and priorities`() {
        assignmentProblemApplicationServiceImpl.solveProblem(priorities, capacities)

        verify(priorityApplicationService).loadPriorities(priorities)
        verify(subjectApplicationService).loadCapacities(capacities)
    }
}
