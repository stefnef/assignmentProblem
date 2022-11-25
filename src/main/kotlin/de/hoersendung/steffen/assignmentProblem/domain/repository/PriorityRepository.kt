package de.hoersendung.steffen.assignmentProblem.domain.repository

import de.hoersendung.steffen.assignmentProblem.domain.model.entity.Priority


interface PriorityRepository {
    fun add(priority: Priority)
    fun getAll(): List<Priority>

}
