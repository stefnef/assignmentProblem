package de.hoersendung.steffen.assignmentProblem.defaults

import de.hoersendung.steffen.assignmentProblem.domain.entity.Subject
import de.hoersendung.steffen.assignmentProblem.domain.valueObject.Capacity
import de.hoersendung.steffen.assignmentProblem.domain.valueObject.SubjectName

fun simpleSubject(name: String = "SubjectName", capacity: Int = 1) = Subject(SubjectName(name), Capacity(capacity))

fun threeSubjects() = listOf(
    simpleSubject("sub_1"),
    simpleSubject("sub_2"),
    simpleSubject("sub_3")
)
