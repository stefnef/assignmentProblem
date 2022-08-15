package de.hoersendung.steffen.assignmentProblem.defaults

import de.hoersendung.steffen.assignmentProblem.domain.entity.Assignment
import de.hoersendung.steffen.assignmentProblem.domain.entity.Priority
import de.hoersendung.steffen.assignmentProblem.domain.entity.Pupil
import de.hoersendung.steffen.assignmentProblem.domain.entity.Subject
import de.hoersendung.steffen.assignmentProblem.domain.valueObject.Capacity
import de.hoersendung.steffen.assignmentProblem.domain.valueObject.PriorityValue
import de.hoersendung.steffen.assignmentProblem.domain.valueObject.PupilName
import de.hoersendung.steffen.assignmentProblem.domain.valueObject.SubjectName

fun simplePriority(pupilName: String = "Bob",
                   quartal: String = "Q1",
                   subjectName: String = "Sub_1",
                   capacity: Int = 1,
                   priorityValue: Int = -1 ) =
    Priority(
        Assignment(
            Pupil(PupilName("${pupilName}_$quartal")),
            Subject(SubjectName(subjectName), Capacity(capacity))),
        PriorityValue(priorityValue)
)

fun prioritiesTwoPupilsTwoSubjects() = listOf(
    simplePriority(pupilName = "Anna", quartal = "Q1", subjectName = "Sub_First", priorityValue = 1),
    simplePriority(pupilName = "Anna", quartal = "Q1", subjectName = "Sub_Second", priorityValue = 2 ),
    simplePriority(pupilName = "Bob",  quartal = "Q2", subjectName = "Sub_First", priorityValue = 10),
    simplePriority(pupilName = "Bob",  quartal = "Q2", subjectName = "Sub_Second", priorityValue = 20 )
)

fun threePriorities() = listOf(
    simplePriority(pupilName = "Anna"),
    simplePriority(pupilName = "Bob"),
    simplePriority(pupilName = "Carl")
)
