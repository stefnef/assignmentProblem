package de.hoersendung.steffen.assignmentProblem.domain.entity

import de.hoersendung.steffen.assignmentProblem.domain.valueObject.PriorityValue
import de.hoersendung.steffen.assignmentProblem.domain.valueObject.SubjectName

data class SolutionAssignment(val student: Student, val subjectName: SubjectName, val priorityValue: PriorityValue)
