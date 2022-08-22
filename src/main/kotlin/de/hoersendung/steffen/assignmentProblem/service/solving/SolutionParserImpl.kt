package de.hoersendung.steffen.assignmentProblem.service.solving

import de.hoersendung.steffen.assignmentProblem.domain.entity.SolutionAssignment
import de.hoersendung.steffen.assignmentProblem.domain.entity.Student
import de.hoersendung.steffen.assignmentProblem.domain.valueObject.PriorityValue
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

    fun parseToList(solutionText: String): List<SolutionAssignment> {
        val optimalSolution = extractSolutionInformation(solutionText)
        val assignments = mutableListOf<SolutionAssignment>()

            optimalSolution.lines().forEach {line ->
                if (line.contains("$")) {
                    assignments.add(line.parseSolutionAssignment())
                }
            }
        return assignments
    }

    private fun String.parseSolutionAssignment(): SolutionAssignment {
        val solutionValues = split("$")
        return SolutionAssignment(
            Student(solutionValues.parseStudentName()),
            solutionValues.parseSubjectName(),
            solutionValues.parsePriorityValue()
        )
    }

    fun List<String>.parseStudentName() : StudentName {
        return StudentName(this[1])
    }

    fun List<String>.parseSubjectName() : SubjectName {
        return SubjectName(this[2].substringBefore(" "))
    }

    fun List<String>.parsePriorityValue() : PriorityValue {
        return PriorityValue(this[2].substringAfter("(obj:").substringBefore(")").toInt())
    }
}
