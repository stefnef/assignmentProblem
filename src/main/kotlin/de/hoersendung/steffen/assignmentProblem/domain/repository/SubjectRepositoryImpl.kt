package de.hoersendung.steffen.assignmentProblem.domain.repository

import de.hoersendung.steffen.assignmentProblem.domain.model.entity.Subject
import org.springframework.stereotype.Service

@Service
class SubjectRepositoryImpl(
    private val subjects : MutableList<Subject> = mutableListOf() ) : SubjectRepository {

    override fun add(subject: Subject) {
        subjects.add(subject)
    }

    override fun getAll(): List<Subject> {
        return subjects.toList()
    }
}
