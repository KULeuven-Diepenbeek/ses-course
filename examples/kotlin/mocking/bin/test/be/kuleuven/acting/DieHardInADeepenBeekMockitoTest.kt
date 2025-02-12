package be.kuleuven.acting

import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when` as When
import org.mockito.Mockito.mock

class DieHardInADeepenBeekMockitoTest {
    @Test
    fun `Given failing backflip When recording act one Then redo the whole thing`() {
        // 1. Arrange
        val actor = mock(IllBeBack::class.java)
        When(actor.doBackFlip()).thenReturn(false)
        val movie = DieHardInADeepenBeek(actor)

        // 2/3 act/assert in one
        assertThrows(RuntimeException::class.java) { movie.recordActOne() }
    }

    @Test
    fun `Given a good backflip When recording act one Then its a success`() {
        // 1. Arrange
        val actor = mock(IllBeBack::class.java)
        val movie = DieHardInADeepenBeek(actor)
        When(actor.doBackFlip()).thenReturn(true)

        // 2/3 act/assert in one
        assertDoesNotThrow { movie.recordActOne() }
    }
}