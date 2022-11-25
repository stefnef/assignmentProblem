package de.hoersendung.steffen.assignmentProblem.util.process

import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.File

@Service
class ProcessRunnerImpl : ProcessRunner {

    override fun exec(commands: Array<String>, directory: String): String {
        val dir = File(directory)

        val run = Runtime.getRuntime()
        val proc = run.exec(commands, emptyArray(), dir)

        return proc.inputStream.bufferedReader().use(BufferedReader::readText)
    }
}
