package de.hoersendung.steffen.assignmentProblem.domain.service.priority

import de.hoersendung.steffen.assignmentProblem.domain.model.entity.Assignment
import de.hoersendung.steffen.assignmentProblem.domain.model.entity.Priority
import de.hoersendung.steffen.assignmentProblem.domain.model.entity.Student
import de.hoersendung.steffen.assignmentProblem.domain.model.entity.Subject
import de.hoersendung.steffen.assignmentProblem.domain.model.valueObject.PriorityValue
import de.hoersendung.steffen.assignmentProblem.domain.model.valueObject.StudentName
import de.hoersendung.steffen.assignmentProblem.domain.model.valueObject.SubjectName
import de.hoersendung.steffen.assignmentProblem.domain.repository.PriorityRepository
import de.hoersendung.steffen.assignmentProblem.domain.service.subject.SubjectService
import de.hoersendung.steffen.assignmentProblem.util.io.FileWriter
import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.File

@Service
class PriorityServiceImpl(
    private val subjectService: SubjectService,
    private val repository: PriorityRepository,
    private val fileWriter: FileWriter
) : PriorityService {

    override fun loadPriorities(priorities: File) {
        priorities.bufferedReader().apply {
            val subjects = parseSubjects()

            parsePriorities(subjects)

            val savedPriorities = repository.getAll()
            if (savedPriorities.isEmpty()) {
                throw IllegalArgumentException("file of priorities contains no or wrong formatted priority values")
            }

            fileWriter.writeStudentsData(savedPriorities)
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
        val subjects = header.split(SEPARATION).drop(2)

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
        val prioritiesOfStudent = line.split(SEPARATION)
        val lineIndex = readLines + 1
        val numberOfExpectedSubjects = subjects.size

        if (prioritiesOfStudent.size != (numberOfExpectedSubjects + 2)) {
            throw IllegalArgumentException(
                "could not parse file of priorities: " +
                        "Number of subjects in $lineIndex. line is not as expected ($numberOfExpectedSubjects). Check priority file"
            )
        }
        val studentName = StudentName("${prioritiesOfStudent[0]}_${prioritiesOfStudent[1]}")

        subjects.forEachIndexed { subjectIndex, subjectName ->
            try {
                readPriorityForSubject(SubjectName(subjectName), subjectIndex, prioritiesOfStudent, studentName)
            } catch (e: Exception) {
                throw IllegalArgumentException("could not parse file of priorities: ${e.message}")
            }
        }
        return lineIndex
    }

    private fun readPriorityForSubject(
        subjectName: SubjectName,
        subjectIndex: Int,
        prioritiesOfStudent: List<String>,
        studentName: StudentName
    ) {
        val capacity = subjectService.getCapacityForSubject(subjectName)

        val priorityValue = PriorityValue(prioritiesOfStudent[subjectIndex + 2].toInt())

        repository.add(
            Priority(
                Assignment(
                    student = Student(studentName),
                    Subject(subjectName, capacity)
                ),
                priorityValue
            )
        )
    }

    companion object{
        private const val SEPARATION = ";"
    }
}
