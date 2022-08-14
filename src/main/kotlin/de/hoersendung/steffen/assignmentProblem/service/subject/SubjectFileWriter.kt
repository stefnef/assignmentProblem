package de.hoersendung.steffen.assignmentProblem.service.subject

import de.hoersendung.steffen.assignmentProblem.domain.entity.Subject

interface SubjectFileWriter {
    fun write(subjects: List<Subject>)

}
