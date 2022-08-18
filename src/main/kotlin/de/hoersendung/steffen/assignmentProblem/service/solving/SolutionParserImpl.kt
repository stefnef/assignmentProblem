package de.hoersendung.steffen.assignmentProblem.service.solving

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
}
