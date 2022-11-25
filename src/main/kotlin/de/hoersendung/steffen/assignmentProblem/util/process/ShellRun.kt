package de.hoersendung.steffen.assignmentProblem.util.process

interface ShellRun {

    fun execute(): String
    fun isSolverConfigured(): Boolean

}
