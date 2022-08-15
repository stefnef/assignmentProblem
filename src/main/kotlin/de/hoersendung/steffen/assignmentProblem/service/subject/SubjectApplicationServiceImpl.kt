package de.hoersendung.steffen.assignmentProblem.service.subject

import de.hoersendung.steffen.assignmentProblem.domain.entity.Subject
import de.hoersendung.steffen.assignmentProblem.domain.valueObject.Capacity
import de.hoersendung.steffen.assignmentProblem.domain.valueObject.SubjectName
import de.hoersendung.steffen.assignmentProblem.repository.SubjectRepository
import de.hoersendung.steffen.assignmentProblem.service.FileWriter
import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.File

@Service
class SubjectApplicationServiceImpl(
    private val repository : SubjectRepository,
    private val fileWriter : FileWriter
) : SubjectApplicationService {

    override fun loadCapacities(capacities: File) {
        capacities.bufferedReader().apply {
            val subjectNames =  parseLine("file of capacities is empty")
            val capacityValues = parseLine("file of capacities contains no or wrong formatted capacity values")

            if (subjectNames.size != capacityValues.size) {
                throw IllegalArgumentException("file of capacities differs in number of subjects (${subjectNames.size}) " +
                        "and number of capacities (${capacityValues.size})")
            }

            subjectNames.forEachIndexed { index, subjectName ->
                repository.add(Subject(SubjectName(subjectName), Capacity(capacityValues[index].toInt())))
            }

        }

        fileWriter.write(repository.getAll())
    }

    override fun getCapacityForSubject(subjectName: SubjectName): Capacity {
        return try {
            repository.getAll().first { it.name == subjectName }.capacity
        } catch (e: NoSuchElementException){
            throw IllegalArgumentException("Could not find any subject with name '${subjectName.value}'")
        }
    }

    override fun getNumberOfSubjects(): Int {
        return repository.getAll().size
    }

    private fun BufferedReader.parseLine(errorMessage: String): List<String> {
        val line = this.readLine() ?: throw IllegalArgumentException(errorMessage)
        return line.split(",")
    }
}
