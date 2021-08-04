package be.kuleuven.acting

import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import java.lang.RuntimeException

class DieHardInADeepenBeekTest {

    @Test
    fun `Given failing backflip When recording act one Then redo the whole thing`() {
        // 1. Arrange
        val movie = DieHardInADeepenBeek(ArnieLookalike())

        // 2/3 act/assert in one
        assertThrows(RuntimeException::class.java) { movie.recordActOne() }
    }

    @Test
    fun `Given a good backflip When recording act one Then its a success`() {
        // 1. Arrange
        val movie = DieHardInADeepenBeek(StuntmanArnie())

        // 2/3 act/assert in one
        assertDoesNotThrow { movie.recordActOne() }
    }
}