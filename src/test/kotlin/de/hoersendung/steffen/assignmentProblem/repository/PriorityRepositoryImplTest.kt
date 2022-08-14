package de.hoersendung.steffen.assignmentProblem.repository

import de.hoersendung.steffen.assignmentProblem.defaults.simplePriority
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class PriorityRepositoryImplTest {

    private val priorityRepository = PriorityRepositoryImpl()

    @Test
    internal fun `should add Prioritys`() {
        priorityRepository.add(simplePriority(pupilName = "Alice"))
        priorityRepository.add(simplePriority(pupilName = "Bob"))
        priorityRepository.add(simplePriority(pupilName = "Carl"))

        val priorities = priorityRepository.getAll()
        assertThat(priorities.size).isEqualTo(3)
        assertThat(priorities[0].assignment.pupil.name.value).isEqualTo("Alice_Q1")
        assertThat(priorities[1].assignment.pupil.name.value).isEqualTo("Bob_Q1")
        assertThat(priorities[2].assignment.pupil.name.value).isEqualTo("Carl_Q1")
    }
}
