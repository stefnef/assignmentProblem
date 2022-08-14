package de.hoersendung.steffen.assignmentProblem.defaults

import java.io.File

const val BASE_PATH = "src/test/files"
const val CAPACITY_PATH = "$BASE_PATH/capacity"
const val PRIORITY_PATH = "$BASE_PATH/priority"

fun testingEmptyFile() = File("$BASE_PATH/empty.csv")

fun testingCapacitiesFile() = File("$CAPACITY_PATH/capacities.csv")

fun testingCapacitiesMissing2ndLineFile() = File("$CAPACITY_PATH/capacitiesMissing2ndLine.csv")

fun testingCapacitiesWrongNumberFile() = File("$CAPACITY_PATH/capacitiesWrongNumberOfCapacities.csv")

fun testingPrioritiesOneFile() = File("$PRIORITY_PATH/OnePriority.csv")

fun testingPrioritiesTwoFile() = File("$PRIORITY_PATH/TwoPriorities.csv")

fun testingPrioritiesMissing2ndLineFile() = File("$PRIORITY_PATH/prioritiesMissing2ndLine.csv")
