package de.hoersendung.steffen.assignmentProblem.defaults

import de.hoersendung.steffen.assignmentProblem.domain.entity.Assignment
import de.hoersendung.steffen.assignmentProblem.domain.entity.Priority
import de.hoersendung.steffen.assignmentProblem.domain.entity.Student
import de.hoersendung.steffen.assignmentProblem.domain.entity.Subject
import de.hoersendung.steffen.assignmentProblem.domain.valueObject.Capacity
import de.hoersendung.steffen.assignmentProblem.domain.valueObject.PriorityValue
import de.hoersendung.steffen.assignmentProblem.domain.valueObject.StudentName
import de.hoersendung.steffen.assignmentProblem.domain.valueObject.SubjectName

fun simplePriority(studentName: String = "Bob",
                   quartal: String = "Q1",
                   subjectName: String = "Sub_1",
                   capacity: Int = 1,
                   priorityValue: Int = -1 ) =
    Priority(
        Assignment(
            Student(StudentName("${studentName}_$quartal")),
            Subject(SubjectName(subjectName), Capacity(capacity))),
        PriorityValue(priorityValue)
)

fun prioritiesTwoStudentsTwoSubjects() = listOf(
    simplePriority(studentName = "Anna", quartal = "Q1", subjectName = "Sub_First", priorityValue = 1),
    simplePriority(studentName = "Anna", quartal = "Q1", subjectName = "Sub_Second", priorityValue = 2 ),
    simplePriority(studentName = "Bob",  quartal = "Q2", subjectName = "Sub_First", priorityValue = 10),
    simplePriority(studentName = "Bob",  quartal = "Q2", subjectName = "Sub_Second", priorityValue = 20 )
)

fun threePriorities() = listOf(
    simplePriority(studentName = "Anna"),
    simplePriority(studentName = "Bob"),
    simplePriority(studentName = "Carl")
)
