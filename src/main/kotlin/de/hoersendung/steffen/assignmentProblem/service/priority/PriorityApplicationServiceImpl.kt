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
    private val subjectService: SubjectApplicationService,
    private val repository: PriorityRepository
) : PriorityApplicationService {

    override fun loadPriorities(priorities: File) {
        priorities.bufferedReader().apply {
            val header = readLine() ?: throw IllegalArgumentException("file of priorities is empty")
            val subjects = header.split(",").drop(2)
            var readPriorities = false

            this.forEachLine {
                readPriorities = true
                val prioritiesOfPupil = it.split(",") //TODO handle null
                val pupilName = PupilName("${prioritiesOfPupil[0]}_${prioritiesOfPupil[1]}")

                subjects.forEachIndexed { subjectIndex, subjectName ->
                    readPriorityForSubject(SubjectName(subjectName), subjectIndex, prioritiesOfPupil, pupilName)
                }
            }

            if (!readPriorities) {
                throw IllegalArgumentException("file of priorities contains no or wrong formatted priority values")
            }

        }
    }

    private fun readPriorityForSubject(
        subjectName: SubjectName,
        subjectIndex: Int,
        prioritiesOfPupil: List<String>,
        pupilName: PupilName
    ) {
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
