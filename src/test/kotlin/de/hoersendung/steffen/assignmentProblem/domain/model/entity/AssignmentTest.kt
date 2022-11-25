package de.hoersendung.steffen.assignmentProblem.domain.model.entity

import de.hoersendung.steffen.assignmentProblem.domain.model.valueObject.Capacity
import de.hoersendung.steffen.assignmentProblem.domain.model.valueObject.StudentName
import de.hoersendung.steffen.assignmentProblem.domain.model.valueObject.SubjectName
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class AssignmentTest {

    @Test
    internal fun `should use student and subject name as id`() {
        val first = Assignment(Student(StudentName("Anna")), Subject(SubjectName("First"), Capacity(1)))
        val sameStudentSameSubject = Assignment(Student(StudentName("Anna")), Subject(SubjectName("First"), Capacity(1)))
        val sameStudentDifferentSubject = Assignment(Student(StudentName("Anna")), Subject(SubjectName("Second"), Capacity(1)))
        val differentStudentSametSubject = Assignment(Student(StudentName("Bob")), Subject(SubjectName("First"), Capacity(1)))
        val differentStudentDifferentSubject = Assignment(Student(StudentName("Bob")), Subject(SubjectName("Second"), Capacity(1)))

        assertThat(first).isEqualTo(sameStudentSameSubject)
        assertThat(first).isNotEqualTo(sameStudentDifferentSubject)
        assertThat(first).isNotEqualTo(differentStudentSametSubject)
        assertThat(first).isNotEqualTo(differentStudentDifferentSubject)
    }

    @Test
    internal fun `should use student and subject name for hash`() {
        val first = Assignment(Student(StudentName("Anna")), Subject(SubjectName("First"), Capacity(1)))
        val sameStudentSameSubject = Assignment(Student(StudentName("Anna")), Subject(SubjectName("First"), Capacity(1)))
        val sameStudentDifferentSubject = Assignment(Student(StudentName("Anna")), Subject(SubjectName("Second"), Capacity(1)))
        val differentStudentSametSubject = Assignment(Student(StudentName("Bob")), Subject(SubjectName("First"), Capacity(1)))
        val differentStudentDifferentSubject = Assignment(Student(StudentName("Bob")), Subject(SubjectName("Second"), Capacity(1)))

        assertThat(first.hashCode()).isEqualTo(sameStudentSameSubject.hashCode())
        assertThat(first.hashCode()).isNotEqualTo(sameStudentDifferentSubject.hashCode())
        assertThat(first.hashCode()).isNotEqualTo(differentStudentSametSubject.hashCode())
        assertThat(first.hashCode()).isNotEqualTo(differentStudentDifferentSubject.hashCode())
    }
}
