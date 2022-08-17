package de.hoersendung.steffen.assignmentProblem.domain.entity

data class Assignment( val student: Student, val subject: Subject) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Assignment

        if (student != other.student) return false
        if (subject != other.subject) return false

        return true
    }

    override fun hashCode(): Int {
        var result = student.hashCode()
        result = 31 * result + subject.hashCode()
        return result
    }
}
