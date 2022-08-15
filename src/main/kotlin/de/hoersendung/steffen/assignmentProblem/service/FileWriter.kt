package de.hoersendung.steffen.assignmentProblem.service

import de.hoersendung.steffen.assignmentProblem.domain.entity.Priority
import de.hoersendung.steffen.assignmentProblem.domain.entity.Subject

interface FileWriter {
    fun write(subjects: List<Subject>) //TODO rename to writeSubjectsData
    fun writePupilsData(priorities: List<Priority>)
    fun writePriorityData(priorities: List<Priority>)

}
