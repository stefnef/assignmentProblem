package de.hoersendung.steffen.assignmentProblem.util.io

import de.hoersendung.steffen.assignmentProblem.configuration.OutputConfiguration
import de.hoersendung.steffen.assignmentProblem.domain.model.entity.Priority
import de.hoersendung.steffen.assignmentProblem.domain.model.entity.SolutionAssignment
import de.hoersendung.steffen.assignmentProblem.domain.model.entity.Subject
import org.slf4j.Logger
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import java.io.File
import java.io.FileOutputStream

@Service
class FileWriterImpl(
    private val logger: Logger,
    private val outputConfiguration: OutputConfiguration) : FileWriter {

    override fun writeSubjectsData(subjects: List<Subject>) {
        createOutputDirectoryIfNotExists()

        FileOutputStream("${outputConfiguration.directory}/subjects.data").apply {
            val writer = bufferedWriter()
            writer.write("# SubjectName Capacity\n")
            subjects.forEach {
                writer.write("${it.name.value} ${it.capacity.value}\n")
            }
            writer.flush()
        }
        logger.info("wrote subjects data file (${outputConfiguration.directory}/subjects.data)")
    }

    override fun writeStudentsData(priorities: List<Priority>) {
        createOutputDirectoryIfNotExists()

        FileOutputStream("${outputConfiguration.directory}/students.data").apply {
            val writer = bufferedWriter()
            writer.write("# StudentName_Quartal\n")
            priorities.map { it.assignment.student }.toSet().forEach { student ->
                writer.write("${student.name.value}\n")
            }
            writer.flush()
        }
        logger.info("wrote students data file (${outputConfiguration.directory}/students.data)")
    }

    override fun writePriorityData(priorities: List<Priority>) {
        createOutputDirectoryIfNotExists()
        FileOutputStream("${outputConfiguration.directory}/priorities.data").apply {
            val writer = bufferedWriter()
            writer.write("# StudentName_Quartal Subject Priority\n")
            priorities.forEach {
                writer.write("${it.assignment.student.name.value} ${it.assignment.subject.name.value} ")
                writer.write("${it.priorityValue.value}\n")
            }
            writer.flush()
        }
        logger.info("wrote priorities data file (${outputConfiguration.directory}/priorities.data)")
    }

    override fun copyLinearProgramm() {
        createOutputDirectoryIfNotExists()

        val resource = ClassPathResource("assignmentProblem.zpl")
        val linearProgramm = File("${outputConfiguration.directory}/assignmentProblem.zpl")

        resource.inputStream.use { input ->
           linearProgramm.outputStream().use { output ->
               input.copyTo(output)
           }
        }
        logger.info("copied zpl file (${outputConfiguration.directory}/assignmentProblem.zpl)")
    }

    override fun writeSolution(solution: List<SolutionAssignment>) {
        FileOutputStream("${outputConfiguration.directory}/solution.csv").apply {
            val writer = bufferedWriter()
            writer.write("Student, Quartal, Subject, Priority\n")
            solution.sortedWith(compareBy( { it.quartal.value }, {it.student.value} )).forEach {
                writer.write("${it.student.value},")
                writer.write("${it.quartal.value},")
                writer.write("${it.subjectName.value},")
                writer.write("${it.priorityValue.value}\n")
            }
            writer.flush()
        }
        logger.info("wrote solution file (${outputConfiguration.directory}/solution.csv)")
    }

    private fun createOutputDirectoryIfNotExists() {
        val directory = File(outputConfiguration.directory)
        if (!directory.exists()) {
            directory.mkdirs()
        }
    }
}
