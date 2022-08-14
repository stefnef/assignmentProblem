package de.hoersendung.steffen.assignmentProblem.service.priority

import de.hoersendung.steffen.assignmentProblem.defaults.testingPrioritiesOneFile
import de.hoersendung.steffen.assignmentProblem.domain.entity.Assignment
import de.hoersendung.steffen.assignmentProblem.domain.entity.Priority
import de.hoersendung.steffen.assignmentProblem.domain.entity.Pupil
import de.hoersendung.steffen.assignmentProblem.domain.entity.Subject
import de.hoersendung.steffen.assignmentProblem.domain.valueObject.Capacity
import de.hoersendung.steffen.assignmentProblem.domain.valueObject.PriorityValue
import de.hoersendung.steffen.assignmentProblem.domain.valueObject.PupilName
import de.hoersendung.steffen.assignmentProblem.domain.valueObject.SubjectName
import de.hoersendung.steffen.assignmentProblem.repository.PriorityRepository
import de.hoersendung.steffen.assignmentProblem.service.subject.SubjectApplicationService
import org.junit.jupiter.api.Test
import org.mockito.kotlin.given
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

internal class PriorityApplicationServiceImplTest {

    private val repository: PriorityRepository = mock()
    private val subjectService : SubjectApplicationService = mock()
    private val priorityService = PriorityApplicationServiceImpl(subjectService, repository)

    @Test
    internal fun `should add one Priority to repository`() {
        given(subjectService.getCapacityForSubject(SubjectName("Sportkurs_1"))).willReturn(Capacity(1))
        given(subjectService.getCapacityForSubject(SubjectName("Sportkurs_2"))).willReturn(Capacity(2))
        given(subjectService.getCapacityForSubject(SubjectName("Sportkurs_3"))).willReturn(Capacity(3))

        priorityService.loadPriorities(testingPrioritiesOneFile())

        verify(repository).add(
            Priority(
                Assignment(
                    pupil = Pupil(PupilName("Bond_Q1")),
                    subject = Subject(SubjectName("Sportkurs_1"), Capacity(1))
                ),
                PriorityValue(-1)
            ))

        verify(repository).add(
            Priority(
                Assignment(
                    pupil = Pupil(PupilName("Bond_Q1")),
                    subject = Subject(SubjectName("Sportkurs_2"), Capacity(2))
                ),
                PriorityValue(2)
            ))

        verify(repository).add(
            Priority(
                Assignment(
                    pupil = Pupil(PupilName("Bond_Q1")),
                    subject = Subject(SubjectName("Sportkurs_3"), Capacity(3))
                ),
                PriorityValue(1)
            ))
    }


}
