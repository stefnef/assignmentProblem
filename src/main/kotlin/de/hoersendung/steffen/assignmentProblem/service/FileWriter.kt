package de.hoersendung.steffen.assignmentProblem.service

import de.hoersendung.steffen.assignmentProblem.domain.entity.Subject

interface FileWriter {
    fun write(subjects: List<Subject>)

}
