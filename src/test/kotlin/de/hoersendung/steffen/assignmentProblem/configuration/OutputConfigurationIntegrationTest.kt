package de.hoersendung.steffen.assignmentProblem.configuration

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class OutputConfigurationIntegrationTest {

    @Autowired
    private lateinit var outputConfiguration : OutputConfiguration

    @Test
    internal fun `it should load configuration`() {
        assertThat(outputConfiguration.directory).isEqualTo("src/test/files/output/tmp")
    }
}
