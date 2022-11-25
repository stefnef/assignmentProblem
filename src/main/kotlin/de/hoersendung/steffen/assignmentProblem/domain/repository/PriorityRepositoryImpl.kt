package de.hoersendung.steffen.assignmentProblem.domain.repository

import de.hoersendung.steffen.assignmentProblem.domain.model.entity.Priority
import org.springframework.stereotype.Service

@Service
class PriorityRepositoryImpl(
    private val priorities : MutableList<Priority> = mutableListOf()
) : PriorityRepository {

    override fun add(priority: Priority) {
        priorities.add(priority)
    }

    override fun getAll(): List<Priority> {
        return priorities.toList()
    }
}
