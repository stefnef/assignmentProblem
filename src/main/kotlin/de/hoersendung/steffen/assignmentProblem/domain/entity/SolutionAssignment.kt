package de.hoersendung.steffen.assignmentProblem.domain.entity

import de.hoersendung.steffen.assignmentProblem.domain.valueObject.PriorityValue
import de.hoersendung.steffen.assignmentProblem.domain.valueObject.StudentName
import de.hoersendung.steffen.assignmentProblem.domain.valueObject.SubjectName

data class SolutionAssignment(val student: StudentName, val subjectName: SubjectName, val priorityValue: PriorityValue)
