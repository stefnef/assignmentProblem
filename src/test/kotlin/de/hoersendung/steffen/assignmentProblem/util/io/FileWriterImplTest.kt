package de.hoersendung.steffen.assignmentProblem.util.io

import de.hoersendung.steffen.assignmentProblem.configuration.OutputConfiguration
import de.hoersendung.steffen.assignmentProblem.defaults.prioritiesTwoStudentsTwoSubjects
import de.hoersendung.steffen.assignmentProblem.defaults.threeSubjects
import de.hoersendung.steffen.assignmentProblem.domain.model.entity.SolutionAssignment
import de.hoersendung.steffen.assignmentProblem.domain.model.valueObject.PriorityValue
import de.hoersendung.steffen.assignmentProblem.domain.model.valueObject.Quartal
import de.hoersendung.steffen.assignmentProblem.domain.model.valueObject.StudentName
import de.hoersendung.steffen.assignmentProblem.domain.model.valueObject.SubjectName
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.slf4j.Logger
import java.io.File

internal class FileWriterImplTest {

    private val outputDirectory = "src/test/files/output/tmp"
    private val outputConfiguration = OutputConfiguration(directory = outputDirectory)
    private val logger : Logger = mock()

    private val fileWriter = FileWriterImpl(logger, outputConfiguration)

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

        verify(logger).info("wrote subjects data file (src/test/files/output/tmp/subjects.data)")
    }

    @Test
    internal fun `should write students to data file`() {
        fileWriter.writeStudentsData(prioritiesTwoStudentsTwoSubjects())

        val file = File("${outputDirectory}/students.data")
        assertThat(file.exists()).isTrue

        file.inputStream().apply {
            val reader = bufferedReader()
            assertThat(reader.readLine()).isEqualTo("# StudentName_Quartal")
            assertThat(reader.readLine()).isEqualTo("Anna_Q1")
            assertThat(reader.readLine()).isEqualTo("Bob_Q2")
            assertThat(reader.readLine()).isNull()
        }

        verify(logger).info("wrote students data file (src/test/files/output/tmp/students.data)")
    }

    @Test
    internal fun `should write priorities to data file`() {
        fileWriter.writePriorityData(prioritiesTwoStudentsTwoSubjects())

        val file = File("${outputDirectory}/priorities.data")
        assertThat(file.exists()).isTrue

        file.inputStream().apply {
            val reader = bufferedReader()
            assertThat(reader.readLine()).isEqualTo("# StudentName_Quartal Subject Priority")
            assertThat(reader.readLine()).isEqualTo("Anna_Q1 Sub_First 1")
            assertThat(reader.readLine()).isEqualTo("Anna_Q1 Sub_Second 2")
            assertThat(reader.readLine()).isEqualTo("Bob_Q2 Sub_First 10")
            assertThat(reader.readLine()).isEqualTo("Bob_Q2 Sub_Second 20")
            assertThat(reader.readLine()).isNull()
        }
        verify(logger).info("wrote priorities data file (src/test/files/output/tmp/priorities.data)")
    }

    @Test
    internal fun `should copy the linear programm file`() {
        fileWriter.copyLinearProgramm()

        val file = File("${outputDirectory}/assignmentProblem.zpl")
        assertThat(file.exists()).isTrue
        verify(logger).info("copied zpl file (src/test/files/output/tmp/assignmentProblem.zpl)")
    }

    @Test
    internal fun `should copy linear programm if it already exists`() {
        val directory = File(outputConfiguration.directory)
        if (!directory.exists()) {
            directory.mkdirs()
        }

        val oneByte = byteArrayOf(1)
        val file = File("${outputDirectory}/assignmentProblem.zpl")
        file.writeBytes(oneByte)

        fileWriter.copyLinearProgramm()

        val fileInPath = File("${outputDirectory}/assignmentProblem.zpl")
        val linearProgramm = ClassLoader.getSystemResourceAsStream("assignmentProblem.zpl")!!
        assertThat(fileInPath.readBytes()).isEqualTo(linearProgramm.readAllBytes())
    }

    @Test
    internal fun `should write solution to a file sorted by quartal and student name`() {
        val directory = File(outputConfiguration.directory)
        if (!directory.exists()) {
            directory.mkdirs()
        }

        val solution = listOf(
            SolutionAssignment(StudentName("Alice"), Quartal("Q2"), SubjectName("Bouldern"), PriorityValue(999)),
            SolutionAssignment(StudentName("Zelda"), Quartal("Q1"), SubjectName("Stretching"), PriorityValue(1)),
            SolutionAssignment(StudentName("Carl"), Quartal("Q3"), SubjectName("Curling"), PriorityValue(0)),
            SolutionAssignment(StudentName("Bob"), Quartal("Q3"), SubjectName("Billard"), PriorityValue(3))
        )

        fileWriter.writeSolution(solution)

        val file = File("${outputDirectory}/solution.csv")
        assertThat(file.exists()).isTrue

        file.inputStream().apply {
            val reader = bufferedReader()
            assertThat(reader.readLine()).isEqualTo("Student; Quartal; Subject; Priority")
            assertThat(reader.readLine()).isEqualTo("Zelda;Q1;Stretching;1")
            assertThat(reader.readLine()).isEqualTo("Alice;Q2;Bouldern;999")
            assertThat(reader.readLine()).isEqualTo("Bob;Q3;Billard;3")
            assertThat(reader.readLine()).isEqualTo("Carl;Q3;Curling;0")
            assertThat(reader.readLine()).isNull()
        }
        verify(logger).info("wrote solution file (src/test/files/output/tmp/solution.csv)")
    }

    @AfterEach
    fun afterEach() {
        File(outputDirectory).deleteRecursively()
    }
}
