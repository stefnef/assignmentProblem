package de.hoersendung.steffen.assignmentProblem.domain.service.priority

import de.hoersendung.steffen.assignmentProblem.defaults.*
import de.hoersendung.steffen.assignmentProblem.domain.model.entity.Assignment
import de.hoersendung.steffen.assignmentProblem.domain.model.entity.Priority
import de.hoersendung.steffen.assignmentProblem.domain.model.entity.Student
import de.hoersendung.steffen.assignmentProblem.domain.model.entity.Subject
import de.hoersendung.steffen.assignmentProblem.domain.model.valueObject.Capacity
import de.hoersendung.steffen.assignmentProblem.domain.model.valueObject.PriorityValue
import de.hoersendung.steffen.assignmentProblem.domain.model.valueObject.StudentName
import de.hoersendung.steffen.assignmentProblem.domain.model.valueObject.SubjectName
import de.hoersendung.steffen.assignmentProblem.domain.repository.PriorityRepository
import de.hoersendung.steffen.assignmentProblem.domain.service.subject.SubjectService
import de.hoersendung.steffen.assignmentProblem.util.io.FileWriter
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.given
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

internal class PriorityServiceImplTest {

    private val repository: PriorityRepository = mock()
    private val subjectService : SubjectService = mock()
    private val fileWriter: FileWriter = mock()
    private val priorityService = PriorityServiceImpl(subjectService, repository, fileWriter)

    @Test
    internal fun `should add one Priority to repository`() {
        given(subjectService.getNumberOfSubjects()).willReturn(3)
        given(subjectService.getCapacityForSubject(SubjectName("Sportkurs_1"))).willReturn(Capacity(1))
        given(subjectService.getCapacityForSubject(SubjectName("Sportkurs_2"))).willReturn(Capacity(2))
        given(subjectService.getCapacityForSubject(SubjectName("Sportkurs_3"))).willReturn(Capacity(3))
        val mockedListOfPriorities = threePriorities()
        given(repository.getAll()).willReturn(mockedListOfPriorities)

        priorityService.loadPriorities(testingPrioritiesOneFile())

        verify(repository).add(
            Priority(
                Assignment(
                    student = Student(StudentName("Bond_Q1")),
                    subject = Subject(SubjectName("Sportkurs_1"), Capacity(1))
                ),
                PriorityValue(-1)
            )
        )

        verify(repository).add(
            Priority(
                Assignment(
                    student = Student(StudentName("Bond_Q1")),
                    subject = Subject(SubjectName("Sportkurs_2"), Capacity(2))
                ),
                PriorityValue(2)
            )
        )

        verify(repository).add(
            Priority(
                Assignment(
                    student = Student(StudentName("Bond_Q1")),
                    subject = Subject(SubjectName("Sportkurs_3"), Capacity(3))
                ),
                PriorityValue(1)
            )
        )

        verify(fileWriter).writeStudentsData(mockedListOfPriorities)
        verify(fileWriter).writePriorityData(mockedListOfPriorities)
    }

    @Test
    internal fun `should load priorities for more than one student`() {
        given(subjectService.getNumberOfSubjects()).willReturn(2)
        given(subjectService.getCapacityForSubject(SubjectName("Sportkurs_1"))).willReturn(Capacity(1))
        given(subjectService.getCapacityForSubject(SubjectName("Sportkurs_2"))).willReturn(Capacity(2))
        given(repository.getAll()).willReturn(threePriorities())

        priorityService.loadPriorities(testingPrioritiesTwoFile())

        verify(repository).add(
            Priority(
                Assignment(
                    student = Student(StudentName("Bond_Q1")),
                    subject = Subject(SubjectName("Sportkurs_1"), Capacity(1))
                ),
                PriorityValue(-1)
            )
        )

        verify(repository).add(
            Priority(
                Assignment(
                    student = Student(StudentName("Bond_Q1")),
                    subject = Subject(SubjectName("Sportkurs_2"), Capacity(2))
                ),
                PriorityValue(2)
            )
        )

        verify(repository).add(
            Priority(
                Assignment(
                    student = Student(StudentName("Mueller_Q3")),
                    subject = Subject(SubjectName("Sportkurs_1"), Capacity(1))
                ),
                PriorityValue(999)
            )
        )

        verify(repository).add(
            Priority(
                Assignment(
                    student = Student(StudentName("Mueller_Q3")),
                    subject = Subject(SubjectName("Sportkurs_2"), Capacity(2))
                ),
                PriorityValue(-1)
            )
        )
    }

    @Test
    internal fun `should throw exception if file is empty`() {
        Assertions.assertThatThrownBy {
            priorityService.loadPriorities(testingEmptyFile())
        }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("file of priorities is empty")
    }

    @Test
    internal fun `should throw exception if priority lines are missing`() {
        given(subjectService.getNumberOfSubjects()).willReturn(3)
        Assertions.assertThatThrownBy {
            priorityService.loadPriorities(testingPrioritiesMissing2ndLineFile())
        }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("file of priorities contains no or wrong formatted priority values")
    }

    @Test
    internal fun `should propagate exception if subject is unknown`() {
        given(subjectService.getNumberOfSubjects()).willReturn(3)
        given(subjectService.getCapacityForSubject(any())).willThrow(IllegalArgumentException("Fake"))

        Assertions.assertThatThrownBy {
            priorityService.loadPriorities(testingPrioritiesOneFile())
        }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("could not parse file of priorities: Fake")
    }

    @Test
    internal fun `should throw exception if priority line does not match with number of subjects`() {
        given(subjectService.getNumberOfSubjects()).willReturn(2)
        given(subjectService.getCapacityForSubject(SubjectName("Sportkurs_1"))).willReturn(Capacity(1))
        given(subjectService.getCapacityForSubject(SubjectName("Sportkurs_2"))).willReturn(Capacity(2))

        Assertions.assertThatThrownBy {
            priorityService.loadPriorities(testingPrioritiesWrongNumberFile())
        }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("could not parse file of priorities: " +
                    "Number of subjects in 3. line is not as expected (2). Check priority file")
    }

    @Test
    internal fun `should throw exception if header line contains wrong number of subjects`() {
        given(subjectService.getNumberOfSubjects()).willReturn(40)

        Assertions.assertThatThrownBy {
            priorityService.loadPriorities(testingPrioritiesOneFile())
        }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("could not parse file of priorities: " +
                    "Number of given subjects in header line is not as expected (40). Check priority and capacity files")
    }
}
