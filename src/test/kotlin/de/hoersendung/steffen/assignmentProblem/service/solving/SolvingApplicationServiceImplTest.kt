package de.hoersendung.steffen.assignmentProblem.service.solving

import de.hoersendung.steffen.assignmentProblem.service.FileWriter
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

internal class SolvingApplicationServiceImplTest {

    private val fileWrite : FileWriter = mock()
    private val mipSolver : MIPSolver = mock()

    private val solvingApplicationService = SolvingApplicationServiceImpl(fileWrite, mipSolver)

    @Test
    internal fun `should add zpl file from classpath`() {
        solvingApplicationService.solve()

        verify(fileWrite).copyLinearProgramm()
        verify(mipSolver).solve()
    }
}
