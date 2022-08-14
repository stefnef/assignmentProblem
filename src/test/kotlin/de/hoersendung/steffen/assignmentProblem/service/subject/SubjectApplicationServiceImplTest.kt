package de.hoersendung.steffen.assignmentProblem.service.subject

import de.hoersendung.steffen.assignmentProblem.defaults.*
import de.hoersendung.steffen.assignmentProblem.domain.entity.Subject
import de.hoersendung.steffen.assignmentProblem.domain.valueObject.Capacity
import de.hoersendung.steffen.assignmentProblem.domain.valueObject.SubjectName
import de.hoersendung.steffen.assignmentProblem.repository.SubjectRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.mockito.kotlin.given
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

internal class SubjectApplicationServiceImplTest {

    private val repository: SubjectRepository = mock()
    private val subjectFileWriter : SubjectFileWriter = mock()
    private val subjectService = SubjectApplicationServiceImpl(repository, subjectFileWriter)

    @Test
    internal fun `should return number of subjects`() {
        given(repository.getAll()).willReturn(threeSubjects())

        assertThat(subjectService.getNumberOfSubjects()).isEqualTo(3)
    }

    @Test
    internal fun `it should find the correct capacity for given subjectName`() {
        given(repository.getAll()).willReturn(threeSubjects())

        val capacity = subjectService.getCapacityForSubject(SubjectName("sub_2"))
        assertThat(capacity.value).isEqualTo(2)
    }

    @Test
    internal fun `it should throw Exception if capacity was not found for SubjectName`() {
        assertThatThrownBy { subjectService.getCapacityForSubject(SubjectName("sub_2")) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Could not find any subject with name 'sub_2'")
    }

    @Test
    internal fun `should add five subjects to repository`() {
        given(repository.getAll()).willReturn(threeSubjects())

        subjectService.loadCapacities(testingCapacitiesFile())

        verify(repository).add(Subject(SubjectName("Sportkurs_1"), Capacity(1)))
        verify(repository).add(Subject(SubjectName("Sportkurs_2"), Capacity(2)))
        verify(repository).add(Subject(SubjectName("Sportkurs_3"), Capacity(3)))
        verify(repository).add(Subject(SubjectName("Sportkurs_4"), Capacity(4)))
        verify(repository).add(Subject(SubjectName("Schwimmen"), Capacity(1)))
    }

    @Test
    internal fun `should throw exception if file is empty`() {
        assertThatThrownBy {
            subjectService.loadCapacities(testingEmptyFile())
        }.isInstanceOf(IllegalArgumentException::class.java)
         .hasMessage("file of capacities is empty")
    }

    @Test
    internal fun `should throw exception if capacity line is missing`() {
        assertThatThrownBy {
            subjectService.loadCapacities(testingCapacitiesMissing2ndLineFile())
        }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("file of capacities contains no or wrong formatted capacity values")
    }

    @Test
    internal fun `should throw Exception if number of subjects and capacities do not match`() {
        assertThatThrownBy {
            subjectService.loadCapacities(testingCapacitiesWrongNumberFile())
        }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("file of capacities differs in number of subjects (3) and number of capacities (2)")
    }

    @Test
    internal fun `should write subjects to data file`() {
        given(repository.getAll()).willReturn(threeSubjects())

        subjectService.loadCapacities(testingCapacitiesFile())

        verify(subjectFileWriter).write(threeSubjects())
    }
}
