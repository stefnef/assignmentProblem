package de.hoersendung.steffen.assignmentProblem.util.process

interface ProcessRunner {
    fun exec(commands: Array<String>, directory: String) : String

}
