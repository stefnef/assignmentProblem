package de.hoersendung.steffen.assignmentProblem.configuration

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "application.config")
data class ConfigFile(var configFile: String = "")
