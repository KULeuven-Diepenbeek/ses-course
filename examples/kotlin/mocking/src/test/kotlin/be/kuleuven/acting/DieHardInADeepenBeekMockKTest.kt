package be.kuleuven.acting

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class DieHardInADeepenBeekMockKTest {

    @Test
    fun `Given failing backflip When recording act one Then redo the whole thing`() {
        // 1. Arrange
        val actor = mockk<IllBeBack>()
        every { actor.doBackFlip() } returns false

        val movie = DieHardInADeepenBeek(actor)

        // 2/3 act/assert in one
        Assertions.assertThrows(RuntimeException::class.java) { movie.recordActOne() }
    }

    @Test
    fun `Given a good backflip When recording act one Then its a success`() {
        // 1. Arrange
        val actor = mockk<IllBeBack>()
        every { actor.doBackFlip() } returns true

        val movie = DieHardInADeepenBeek(actor)

        // 2/3 act/assert in one
        Assertions.assertDoesNotThrow { movie.recordActOne() }
    }
}