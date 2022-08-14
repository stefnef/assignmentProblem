package de.hoersendung.steffen.assignmentProblem.configuration

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "application.output")
data class OutputConfiguration( var directory: String = "")
