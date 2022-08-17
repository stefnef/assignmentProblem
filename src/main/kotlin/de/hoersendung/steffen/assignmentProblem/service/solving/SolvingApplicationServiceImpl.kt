package de.hoersendung.steffen.assignmentProblem.service.solving

import de.hoersendung.steffen.assignmentProblem.service.FileWriter
import org.springframework.stereotype.Service

@Service
class SolvingApplicationServiceImpl(
    private val fileWrite : FileWriter
) : SolvingApplicationService {

    override fun solve() {
        fileWrite.copyLinearProgramm()
    }
}
