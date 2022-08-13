package de.hoersendung.steffen.assignmentProblem.repository

import de.hoersendung.steffen.assignmentProblem.domain.entity.Subject

interface SubjectRepository {
    fun add(subject: Subject)

}
