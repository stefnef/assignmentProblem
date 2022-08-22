package de.hoersendung.steffen.assignmentProblem.service

import de.hoersendung.steffen.assignmentProblem.configuration.OutputConfiguration
import de.hoersendung.steffen.assignmentProblem.defaults.prioritiesTwoStudentsTwoSubjects
import de.hoersendung.steffen.assignmentProblem.defaults.threeSubjects
import de.hoersendung.steffen.assignmentProblem.domain.entity.SolutionAssignment
import de.hoersendung.steffen.assignmentProblem.domain.valueObject.PriorityValue
import de.hoersendung.steffen.assignmentProblem.domain.valueObject.Quartal
import de.hoersendung.steffen.assignmentProblem.domain.valueObject.StudentName
import de.hoersendung.steffen.assignmentProblem.domain.valueObject.SubjectName
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Disabled
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
    }

    @Test
    internal fun `should copy the linear programm file`() {
        fileWriter.copyLinearProgramm()

        val file = File("${outputDirectory}/assignmentProblem.zpl")
        assertThat(file.exists()).isTrue
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
    @Disabled
    internal fun `should write solution to a file`() {
        val directory = File(outputConfiguration.directory)
        if (!directory.exists()) {
            directory.mkdirs()
        }

        val solution = listOf(
            SolutionAssignment(StudentName("Zelda_Q2"), Quartal("Q1"), SubjectName("Stretching"), PriorityValue(1)),
            SolutionAssignment(StudentName("Alice_Q1"), Quartal("Q1"), SubjectName("Bouldern"), PriorityValue(999)),
            SolutionAssignment(StudentName("Carl_Q3"), Quartal("Q1"), SubjectName("Curling"), PriorityValue(0)),
            SolutionAssignment(StudentName("Bob_Q3"), Quartal("Q1"), SubjectName("Billard"), PriorityValue(3))
        )

        fileWriter.writeSolution(solution)

        val file = File("${outputDirectory}/solution.csv")
        assertThat(file.exists()).isTrue

        file.inputStream().apply {
            val reader = bufferedReader()
            assertThat(reader.readLine()).isEqualTo("Student, Quartal, Subject, Priority")
            assertThat(reader.readLine()).isEqualTo("Anna,Q1,Bouldern,999")
            assertThat(reader.readLine()).isEqualTo("Zelda,Q2,Stretching,1")
            assertThat(reader.readLine()).isEqualTo("Bob,Q3,Billard,3")
            assertThat(reader.readLine()).isEqualTo("Carl,Q3,Curling,0")
            assertThat(reader.readLine()).isNull()
        }
    }

    @AfterEach
    fun afterEach() {
        File(outputDirectory).deleteRecursively()
    }
}
