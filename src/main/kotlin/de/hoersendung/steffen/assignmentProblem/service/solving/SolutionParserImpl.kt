package de.hoersendung.steffen.assignmentProblem.service.solving

import de.hoersendung.steffen.assignmentProblem.domain.entity.SolutionAssignment
import de.hoersendung.steffen.assignmentProblem.domain.valueObject.PriorityValue
import de.hoersendung.steffen.assignmentProblem.domain.valueObject.Quartal
import de.hoersendung.steffen.assignmentProblem.domain.valueObject.StudentName
import de.hoersendung.steffen.assignmentProblem.domain.valueObject.SubjectName
import org.springframework.stereotype.Service

@Service
class SolutionParserImpl() : SolutionParser {

    override fun parse(solutionText: String): List<SolutionAssignment> {
        val optimalSolution = extractSolutionInformation(solutionText)
        val assignments = mutableListOf<SolutionAssignment>()

            optimalSolution.lines().forEach {line ->
                if (line.contains("$")) {
                    assignments.add(line.parseSolutionAssignment())
                }
            }
        return assignments
    }

    private fun extractSolutionInformation(solutionText: String): String {
        return  solutionText.removePrefix().removeSuffix().trimIndent()
    }

    private fun String.removeSuffix() = substringBefore("Statistics")

    private fun String.removePrefix(): String {
        return substringAfter("objective value:")
    }

    private fun String.parseSolutionAssignment(): SolutionAssignment {
        val solutionValues = split("$")
        return SolutionAssignment(
            solutionValues.parseStudentName(),
            solutionValues.parseQuartal(),
            solutionValues.parseSubjectName(),
            solutionValues.parsePriorityValue()
        )
    }

    fun List<String>.parseStudentName() : StudentName {
        return StudentName(this[1].substringBefore("_"))
    }

    fun List<String>.parseQuartal() : Quartal {
        return Quartal(this[1].substringAfter("_"))
    }

    fun List<String>.parseSubjectName() : SubjectName {
        return SubjectName(this[2].substringBefore(" "))
    }

    fun List<String>.parsePriorityValue() : PriorityValue {
        return PriorityValue(this[2].substringAfter("(obj:").substringBefore(")").toInt())
    }
}
