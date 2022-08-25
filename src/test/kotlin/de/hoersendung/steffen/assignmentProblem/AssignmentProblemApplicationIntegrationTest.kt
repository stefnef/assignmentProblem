package de.hoersendung.steffen.assignmentProblem

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class AssignmentProblemApplicationIntegrationTest {

    @Autowired
    private lateinit var app : AssignmentProblemApplication

    private val basePath = "src/test/files"
    private val prioritiesPath = "$basePath/priority"
    private val capacityPath = "$basePath/capacity"

    @Test
    fun contextLoads() {
    }

    @Test
    internal fun `should read configuration file`() {
        val configuration = app.readConfiguration()
        assertThat(configuration.priorities).isEqualTo("$prioritiesPath/priorities.csv")
        assertThat(configuration.capacities).isEqualTo("$capacityPath/capacities.csv")
        assertThat(configuration.scip).isEqualTo("scip")
    }
}
