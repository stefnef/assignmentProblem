package de.hoersendung.steffen.assignmentProblem.configuration

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class ConfigFileIntegrationTest {

    @Autowired
    private lateinit var configFile: ConfigFile

    @Test
    internal fun `it should load configuration file`() {
        assertThat(configFile.configFile).isEqualTo("src/test/files/config.json")
    }
}
