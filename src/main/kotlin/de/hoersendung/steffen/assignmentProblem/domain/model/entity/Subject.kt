package de.hoersendung.steffen.assignmentProblem.domain.model.entity

import de.hoersendung.steffen.assignmentProblem.domain.model.valueObject.Capacity
import de.hoersendung.steffen.assignmentProblem.domain.model.valueObject.SubjectName

data class Subject(val name: SubjectName, val capacity: Capacity) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Subject

        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}
