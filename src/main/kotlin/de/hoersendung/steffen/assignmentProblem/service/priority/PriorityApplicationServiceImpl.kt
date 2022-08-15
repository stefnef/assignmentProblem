package de.hoersendung.steffen.assignmentProblem.service.priority

import de.hoersendung.steffen.assignmentProblem.domain.entity.Assignment
import de.hoersendung.steffen.assignmentProblem.domain.entity.Priority
import de.hoersendung.steffen.assignmentProblem.domain.entity.Pupil
import de.hoersendung.steffen.assignmentProblem.domain.entity.Subject
import de.hoersendung.steffen.assignmentProblem.domain.valueObject.PriorityValue
import de.hoersendung.steffen.assignmentProblem.domain.valueObject.PupilName
import de.hoersendung.steffen.assignmentProblem.domain.valueObject.SubjectName
import de.hoersendung.steffen.assignmentProblem.repository.PriorityRepository
import de.hoersendung.steffen.assignmentProblem.service.FileWriter
import de.hoersendung.steffen.assignmentProblem.service.subject.SubjectApplicationService
import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.File

@Service
class PriorityApplicationServiceImpl(
    private val subjectService: SubjectApplicationService,
    private val repository: PriorityRepository,
    private val fileWriter: FileWriter
) : PriorityApplicationService {

    override fun loadPriorities(priorities: File) {
        priorities.bufferedReader().apply {
            val subjects = parseSubjects()

            parsePriorities(subjects)

            val savedPriorities = repository.getAll()
            if (savedPriorities.isEmpty()) {
                throw IllegalArgumentException("file of priorities contains no or wrong formatted priority values")
            }

            fileWriter.writePupilsData(savedPriorities)
            fileWriter.writePriorityData(savedPriorities)

        }
    }

    private fun BufferedReader.parsePriorities(subjects: List<String>) {
        var readLines = 1
        this.forEachLine {
            readLines = parsePriorityLine(readLines, it, subjects)
        }
    }

    private fun BufferedReader.parseSubjects(): List<String> {
        val numberOfExpectedSubjects = subjectService.getNumberOfSubjects()
        val header = readLine() ?: throw IllegalArgumentException("file of priorities is empty")
        val subjects = header.split(",").drop(2)

        if (subjects.size != numberOfExpectedSubjects) {
            throw java.lang.IllegalArgumentException(
                "could not parse file of priorities: Number of given subjects in header line is not as " +
                        "expected ($numberOfExpectedSubjects). Check priority and capacity files"
            )
        }
        return subjects
    }

    private fun parsePriorityLine(
        readLines: Int,
        line: String,
        subjects: List<String>
    ): Int {
        val prioritiesOfPupil = line.split(",")
        val lineIndex = readLines + 1
        val numberOfExpectedSubjects = subjects.size

        if (prioritiesOfPupil.size != (numberOfExpectedSubjects + 2)) {
            throw IllegalArgumentException(
                "could not parse file of priorities: " +
                        "Number of subjects in $lineIndex. line is not as expected ($numberOfExpectedSubjects). Check priority file"
            )
        }
        val pupilName = PupilName("${prioritiesOfPupil[0]}_${prioritiesOfPupil[1]}")

        subjects.forEachIndexed { subjectIndex, subjectName ->
            try {
                readPriorityForSubject(SubjectName(subjectName), subjectIndex, prioritiesOfPupil, pupilName)
            } catch (e: Exception) {
                throw IllegalArgumentException("could not parse file of priorities: ${e.message}")
            }
        }
        return lineIndex
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
