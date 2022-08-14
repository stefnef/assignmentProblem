package de.hoersendung.steffen.assignmentProblem.repository

import de.hoersendung.steffen.assignmentProblem.domain.entity.Priority


interface PriorityRepository {
    fun add(priority: Priority)
    fun getAll(): List<Priority>

}
