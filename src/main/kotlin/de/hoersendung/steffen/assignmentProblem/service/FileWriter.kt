package de.hoersendung.steffen.assignmentProblem.service

import de.hoersendung.steffen.assignmentProblem.domain.entity.Priority
import de.hoersendung.steffen.assignmentProblem.domain.entity.Subject

interface FileWriter {
    fun writeSubjectsData(subjects: List<Subject>)
    fun writePupilsData(priorities: List<Priority>)
    fun writePriorityData(priorities: List<Priority>)
    fun copyLinearProgramm()

}
