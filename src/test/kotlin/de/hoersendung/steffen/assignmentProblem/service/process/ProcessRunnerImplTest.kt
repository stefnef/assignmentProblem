package de.hoersendung.steffen.assignmentProblem.service.process

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ProcessRunnerImplTest {

    private val processRunner = ProcessRunnerImpl()

    @Test
    internal fun `should execute given commands`() {
        val files = processRunner.exec(arrayOf("ls"), "src/test/files")
        assertThat(files).isNotEmpty
        assertThat(files).contains("capacity")
    }
}
