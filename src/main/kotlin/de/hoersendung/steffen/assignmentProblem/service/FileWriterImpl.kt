package de.hoersendung.steffen.assignmentProblem.service

import de.hoersendung.steffen.assignmentProblem.configuration.OutputConfiguration
import de.hoersendung.steffen.assignmentProblem.domain.entity.Priority
import de.hoersendung.steffen.assignmentProblem.domain.entity.Subject
import org.springframework.stereotype.Service
import java.io.File
import java.io.FileOutputStream

@Service
class FileWriterImpl(
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
    }

    override fun writePupilsData(priorities: List<Priority>) {
        createOutputDirectoryIfNotExists()

        FileOutputStream("${outputConfiguration.directory}/pupils.data").apply {
            val writer = bufferedWriter()
            writer.write("# PupilName_Quartal\n")
            priorities.map { it.assignment.pupil }.toSet().forEach { pupil ->
                writer.write("${pupil.name.value}\n")
            }
            writer.flush()
        }
    }

    override fun writePriorityData(priorities: List<Priority>) {
        createOutputDirectoryIfNotExists()
        FileOutputStream("${outputConfiguration.directory}/priorities.data").apply {
            val writer = bufferedWriter()
            writer.write("# PupilName_Quartal Subject Priority\n")
            priorities.forEach {
                writer.write("${it.assignment.pupil.name.value} ${it.assignment.subject.name.value} ")
                writer.write("${it.priorityValue.value}\n")
            }
            writer.flush()
        }
    }

    override fun copyLinearProgramm() {
        val linearProgrammFile = File(ClassLoader.getSystemResource("assignmentProblem.zpl").file)
        linearProgrammFile.copyTo(File("${outputConfiguration.directory}/assignmentProblem.zpl"))
    }

    private fun createOutputDirectoryIfNotExists() {
        val directory = File(outputConfiguration.directory)
        if (!directory.exists()) {
            directory.mkdirs()
        }
    }
}
