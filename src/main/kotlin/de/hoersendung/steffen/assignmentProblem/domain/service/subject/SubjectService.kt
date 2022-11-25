package de.hoersendung.steffen.assignmentProblem.domain.service.subject

import de.hoersendung.steffen.assignmentProblem.domain.model.valueObject.Capacity
import de.hoersendung.steffen.assignmentProblem.domain.model.valueObject.SubjectName
import java.io.File

interface SubjectService {
    fun loadCapacities(capacities: File)
    fun getCapacityForSubject(subjectName: SubjectName): Capacity
    fun getNumberOfSubjects(): Int

}
