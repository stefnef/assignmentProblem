package de.hoersendung.steffen.assignmentProblem.domain.entity

import de.hoersendung.steffen.assignmentProblem.domain.valueObject.Capacity
import de.hoersendung.steffen.assignmentProblem.domain.valueObject.PupilName
import de.hoersendung.steffen.assignmentProblem.domain.valueObject.SubjectName
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class AssignmentTest {

    @Test
    internal fun `should use pupil and subject name as id`() {
        val first = Assignment(Pupil(PupilName("Anna")), Subject(SubjectName("First"), Capacity(1)))
        val samePupilSameSubject = Assignment(Pupil(PupilName("Anna")), Subject(SubjectName("First"), Capacity(1)))
        val samePupilDifferentSubject = Assignment(Pupil(PupilName("Anna")), Subject(SubjectName("Second"), Capacity(1)))
        val differentPupilSametSubject = Assignment(Pupil(PupilName("Bob")), Subject(SubjectName("First"), Capacity(1)))
        val differentPupilDifferentSubject = Assignment(Pupil(PupilName("Bob")), Subject(SubjectName("Second"), Capacity(1)))

        assertThat(first).isEqualTo(samePupilSameSubject)
        assertThat(first).isNotEqualTo(samePupilDifferentSubject)
        assertThat(first).isNotEqualTo(differentPupilSametSubject)
        assertThat(first).isNotEqualTo(differentPupilDifferentSubject)
    }

    @Test
    internal fun `should use pupil and subject name for hash`() {
        val first = Assignment(Pupil(PupilName("Anna")), Subject(SubjectName("First"), Capacity(1)))
        val samePupilSameSubject = Assignment(Pupil(PupilName("Anna")), Subject(SubjectName("First"), Capacity(1)))
        val samePupilDifferentSubject = Assignment(Pupil(PupilName("Anna")), Subject(SubjectName("Second"), Capacity(1)))
        val differentPupilSametSubject = Assignment(Pupil(PupilName("Bob")), Subject(SubjectName("First"), Capacity(1)))
        val differentPupilDifferentSubject = Assignment(Pupil(PupilName("Bob")), Subject(SubjectName("Second"), Capacity(1)))

        assertThat(first.hashCode()).isEqualTo(samePupilSameSubject.hashCode())
        assertThat(first.hashCode()).isNotEqualTo(samePupilDifferentSubject.hashCode())
        assertThat(first.hashCode()).isNotEqualTo(differentPupilSametSubject.hashCode())
        assertThat(first.hashCode()).isNotEqualTo(differentPupilDifferentSubject.hashCode())
    }
}
