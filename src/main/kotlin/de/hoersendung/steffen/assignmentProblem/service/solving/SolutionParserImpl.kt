package de.hoersendung.steffen.assignmentProblem.service.solving

import de.hoersendung.steffen.assignmentProblem.domain.entity.Assignment
import de.hoersendung.steffen.assignmentProblem.domain.entity.Student
import de.hoersendung.steffen.assignmentProblem.domain.entity.Subject
import de.hoersendung.steffen.assignmentProblem.domain.valueObject.Capacity
import de.hoersendung.steffen.assignmentProblem.domain.valueObject.StudentName
import de.hoersendung.steffen.assignmentProblem.domain.valueObject.SubjectName
import org.springframework.stereotype.Service

@Service
class SolutionParserImpl : SolutionParser {

    override fun parse(solutionText: String): String {

        var solution = extractSolutionInformation(solutionText)
        return solution
    }

    private fun extractSolutionInformation(solutionText: String): String {
        return  solutionText.removePrefix().removeSuffix().trimIndent()
    }

    private fun String.removeSuffix() = substringBefore("Statistics")

    private fun String.removePrefix(): String {
        return substringAfter("objective value:")
    }

    fun parseToList(solutionText: String): List<Assignment> {
        val optimalSolution = extractSolutionInformation(solutionText)
        val assignments = mutableListOf<Assignment>()


            optimalSolution.lines().forEach {line ->
                if (line.contains("$")) {
                    val info = line.split("$") //TODO do not name it 'info'
                    val assignment = Assignment(
                        Student(StudentName(info[1])),
                        Subject(SubjectName(info[2]), Capacity(1))
                    ) //TODO handle index //TODO find subject
                    assignments.add(assignment)
                }
            }
        return assignments
    }
}
