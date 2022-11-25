package de.hoersendung.steffen.assignmentProblem.service.process

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.io.BufferedReader
import java.io.File

class ProcessRunnerImplTest {

    @Test
    internal fun `it should start process`() {
        val commandArray = arrayOf("scip","-f","assignmentProblem.zpl")
        val dir = File("output/")

        // Running the above command
        val run = Runtime.getRuntime()
        val proc = run.exec(commandArray, emptyArray(), dir)

        val content = proc.inputStream.bufferedReader().use(BufferedReader::readText)
        assertThat(content).isNotEmpty
        print(content)

    }
}
