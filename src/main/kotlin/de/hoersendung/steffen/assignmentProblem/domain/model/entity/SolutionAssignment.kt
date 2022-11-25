package de.hoersendung.steffen.assignmentProblem.domain.model.entity

import de.hoersendung.steffen.assignmentProblem.domain.model.valueObject.PriorityValue
import de.hoersendung.steffen.assignmentProblem.domain.model.valueObject.Quartal
import de.hoersendung.steffen.assignmentProblem.domain.model.valueObject.StudentName
import de.hoersendung.steffen.assignmentProblem.domain.model.valueObject.SubjectName

data class SolutionAssignment(
    val student: StudentName,
    val quartal: Quartal,
    val subjectName: SubjectName,
    val priorityValue: PriorityValue
)
