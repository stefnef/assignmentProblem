package de.hoersendung.steffen.assignmentProblem.service

import de.hoersendung.steffen.assignmentProblem.configuration.OutputConfiguration
import de.hoersendung.steffen.assignmentProblem.domain.entity.Subject
import org.springframework.stereotype.Service
import java.io.File
import java.io.FileOutputStream

@Service
class SubjectFileWriterImpl(
    private val outputConfiguration: OutputConfiguration) : SubjectFileWriter {

    override fun write(subjects: List<Subject>) {
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

    private fun createOutputDirectoryIfNotExists() {
        val directory = File(outputConfiguration.directory)
        if (!directory.exists()) {
            directory.mkdirs()
        }
    }
}
