package de.hoersendung.steffen.assignmentProblem.service.process

interface ProcessRunner {
    fun exec(commands: Array<String>, directory: String) : String

}
