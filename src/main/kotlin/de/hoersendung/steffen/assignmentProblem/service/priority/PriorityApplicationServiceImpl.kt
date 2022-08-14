package de.hoersendung.steffen.assignmentProblem.service.priority

import de.hoersendung.steffen.assignmentProblem.domain.entity.Assignment
import de.hoersendung.steffen.assignmentProblem.domain.entity.Priority
import de.hoersendung.steffen.assignmentProblem.domain.entity.Pupil
import de.hoersendung.steffen.assignmentProblem.domain.entity.Subject
import de.hoersendung.steffen.assignmentProblem.domain.valueObject.PriorityValue
import de.hoersendung.steffen.assignmentProblem.domain.valueObject.PupilName
import de.hoersendung.steffen.assignmentProblem.domain.valueObject.SubjectName
import de.hoersendung.steffen.assignmentProblem.repository.PriorityRepository
import de.hoersendung.steffen.assignmentProblem.service.subject.SubjectApplicationService
import org.springframework.stereotype.Service
import java.io.File

@Service
class PriorityApplicationServiceImpl(
    private val subjectService : SubjectApplicationService,
    private val repository: PriorityRepository
) : PriorityApplicationService {

    override fun loadPriorities(priorities: File) {
        priorities.bufferedReader().apply {
            val header = readLine() //TODO handle null
            val subjects = header.split(",").drop(2)

            val prioritiesOfPupil = readLine().split(",") //TODO handle null
            val pupilName = PupilName("${prioritiesOfPupil[0]}_${prioritiesOfPupil[1]}")

            var subjectIndex = 0
            readPriorityForSubject(subjects, subjectIndex, prioritiesOfPupil, pupilName)

            subjectIndex++
            readPriorityForSubject(subjects, subjectIndex, prioritiesOfPupil, pupilName)

            subjectIndex++
            readPriorityForSubject(subjects, subjectIndex, prioritiesOfPupil, pupilName)
        }
    }

    private fun readPriorityForSubject(
        subjects: List<String>,
        subjectIndex: Int,
        prioritiesOfPupil: List<String>,
        pupilName: PupilName
    ) {
        val subjectName = SubjectName(subjects[subjectIndex])
        val capacity = subjectService.getCapacityForSubject(subjectName)

        val priorityValue = PriorityValue(prioritiesOfPupil[subjectIndex + 2].toInt())

        repository.add(
            Priority(
                Assignment(
                    pupil = Pupil(pupilName),
                    Subject(subjectName, capacity)
                ),
                priorityValue
            )
        )
    }
}
