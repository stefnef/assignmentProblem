package de.hoersendung.steffen.assignmentProblem.service

import de.hoersendung.steffen.assignmentProblem.configuration.OutputConfiguration
import de.hoersendung.steffen.assignmentProblem.defaults.threePriorities
import de.hoersendung.steffen.assignmentProblem.defaults.threeSubjects
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import java.io.File

internal class FileWriterImplTest {

    private val outputDirectory = "src/test/files/output/tmp"
    private val outputConfiguration = OutputConfiguration(directory = outputDirectory)

    private val fileWriter = FileWriterImpl(outputConfiguration)

    @Test
    internal fun `it should write subjects to data file`() {
        fileWriter.writeSubjectsData(threeSubjects())

        val file = File("${outputDirectory}/subjects.data")
        assertThat(file.exists()).isTrue

        file.inputStream().apply {
            val reader = bufferedReader()
            assertThat(reader.readLine()).isEqualTo("# SubjectName Capacity")
            assertThat(reader.readLine()).isEqualTo("sub_1 1")
            assertThat(reader.readLine()).isEqualTo("sub_2 2")
            assertThat(reader.readLine()).isEqualTo("sub_3 3")
            assertThat(reader.readLine()).isNull()
        }
    }

    @Test
    internal fun `should write pupils to data file`() {
        fileWriter.writePupilsData(threePriorities())

        val file = File("${outputDirectory}/pupils.data")
        assertThat(file.exists()).isTrue

        file.inputStream().apply {
            val reader = bufferedReader()
            assertThat(reader.readLine()).isEqualTo("# PupilName_Quartal")
            assertThat(reader.readLine()).isEqualTo("Anna_Q1")
            assertThat(reader.readLine()).isEqualTo("Bob_Q1")
            assertThat(reader.readLine()).isEqualTo("Carl_Q1")
            assertThat(reader.readLine()).isNull()
        }

    }

    @AfterEach
    fun afterEach() {
        File(outputDirectory).deleteRecursively()
    }
}
