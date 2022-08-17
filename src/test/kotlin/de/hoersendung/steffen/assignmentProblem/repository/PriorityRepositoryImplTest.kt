package de.hoersendung.steffen.assignmentProblem.repository

import de.hoersendung.steffen.assignmentProblem.defaults.simplePriority
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class PriorityRepositoryImplTest {

    private val priorityRepository = PriorityRepositoryImpl()

    @Test
    internal fun `should add Prioritys`() {
        priorityRepository.add(simplePriority(studentName = "Alice"))
        priorityRepository.add(simplePriority(studentName = "Bob"))
        priorityRepository.add(simplePriority(studentName = "Carl"))

        val priorities = priorityRepository.getAll()
        assertThat(priorities.size).isEqualTo(3)
        assertThat(priorities[0].assignment.student.name.value).isEqualTo("Alice_Q1")
        assertThat(priorities[1].assignment.student.name.value).isEqualTo("Bob_Q1")
        assertThat(priorities[2].assignment.student.name.value).isEqualTo("Carl_Q1")
    }
}
