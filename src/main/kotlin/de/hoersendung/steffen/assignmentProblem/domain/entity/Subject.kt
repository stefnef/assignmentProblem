package de.hoersendung.steffen.assignmentProblem.domain.entity

import de.hoersendung.steffen.assignmentProblem.domain.valueObject.Capacity
import de.hoersendung.steffen.assignmentProblem.domain.valueObject.SubjectName

data class Subject(val name: SubjectName, val capacity: Capacity) {

}
//TODO add equals for name
