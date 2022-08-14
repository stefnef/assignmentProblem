package de.hoersendung.steffen.assignmentProblem.service.subject

import de.hoersendung.steffen.assignmentProblem.domain.valueObject.Capacity
import de.hoersendung.steffen.assignmentProblem.domain.valueObject.SubjectName
import java.io.File

interface SubjectApplicationService {
    fun loadCapacities(capacities: File)
    fun getCapacityForSubject(subjectName: SubjectName): Capacity //TODO can be null
    fun getNumberOfSubjects(): Int

}
