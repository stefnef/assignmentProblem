package de.hoersendung.steffen.assignmentProblem.util.io

import de.hoersendung.steffen.assignmentProblem.domain.model.entity.Priority
import de.hoersendung.steffen.assignmentProblem.domain.model.entity.SolutionAssignment
import de.hoersendung.steffen.assignmentProblem.domain.model.entity.Subject

interface FileWriter {
    fun writeSubjectsData(subjects: List<Subject>)
    fun writeStudentsData(priorities: List<Priority>)
    fun writePriorityData(priorities: List<Priority>)
    fun copyLinearProgramm()
    fun writeSolution(solution: List<SolutionAssignment>)

}
