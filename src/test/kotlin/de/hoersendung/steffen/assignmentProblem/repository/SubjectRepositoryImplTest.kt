package de.hoersendung.steffen.assignmentProblem.repository

import de.hoersendung.steffen.assignmentProblem.defaults.simpleSubject
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class SubjectRepositoryImplTest {

    private val subjectRepository = SubjectRepositoryImpl()

    @Test
    internal fun `should add Subjects`() {
        subjectRepository.add(simpleSubject(name = "bouldern"))
        subjectRepository.add(simpleSubject(name = "basketball"))
        subjectRepository.add(simpleSubject(name = "tennis"))

        val subjects = subjectRepository.getAll()
        assertThat(subjects.size).isEqualTo(3)
        assertThat(subjects[0].name.value).isEqualTo("bouldern")
        assertThat(subjects[1].name.value).isEqualTo("basketball")
        assertThat(subjects[2].name.value).isEqualTo("tennis")
    }
}
