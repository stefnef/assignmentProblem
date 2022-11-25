package de.hoersendung.steffen.assignmentProblem.domain.model.entity

import de.hoersendung.steffen.assignmentProblem.domain.model.valueObject.Capacity
import de.hoersendung.steffen.assignmentProblem.domain.model.valueObject.SubjectName
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class SubjectTest {

    @Test
    internal fun `should use name as id`() {
        val first = Subject(SubjectName("First"), Capacity(1))
        val sameName = Subject(SubjectName("First"), Capacity(2))
        val differentName = Subject(SubjectName("Different"), Capacity(1))

        assertThat(first).isEqualTo(sameName)
        assertThat(first).isNotEqualTo(differentName)
        assertThat(sameName).isNotEqualTo(differentName)
    }

    @Test
    internal fun `should use name for hash`() {
        val first = Subject(SubjectName("First"), Capacity(1))
        val sameName = Subject(SubjectName("First"), Capacity(2))
        val differentName = Subject(SubjectName("Different"), Capacity(1))

        assertThat(first.hashCode()).isEqualTo(sameName.hashCode())
        assertThat(first.hashCode()).isNotEqualTo(differentName.hashCode())
        assertThat(sameName.hashCode()).isNotEqualTo(differentName.hashCode())
    }
}
