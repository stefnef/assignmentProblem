package de.hoersendung.steffen.assignmentProblem.domain.repository

import de.hoersendung.steffen.assignmentProblem.domain.model.entity.Subject

interface SubjectRepository {
    fun add(subject: Subject)
    fun getAll(): List<Subject>

}
