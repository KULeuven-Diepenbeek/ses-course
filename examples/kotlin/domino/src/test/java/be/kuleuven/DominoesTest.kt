package be.kuleuven

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class DominoesTest {

    @Test
    fun `ketting bestaat met korter stuk`() {
        val dominoes = mutableListOf(
            Domino(1, 4),
            Domino(2, 6),
            Domino(4, 5),
            Domino(5, 1),
            Domino(3, 5),
        )
        val spel  = Dominoes(dominoes)
        assertTrue(spel.bestaatKettingMet(1, 3))
        assertEquals("(1|4)-(4|5)-(5|3)", spel.bord())
    }

    @Test
    fun `ketting bestaat niet in hele chain van mogelijkheden`() {
        val dominoes = mutableListOf(
            Domino(1, 4),
            Domino(2, 6),
            Domino(4, 5),
            Domino(5, 1),
            Domino(5, 5),
        )
        val spel  = Dominoes(dominoes)
        assertFalse(spel.bestaatKettingMet(1, 3))
    }

    @Test
    fun `ketting bestaat met twee stukken die passen`() {
        val dominoes = mutableListOf(
            Domino(1, 4),
            Domino(4, 2),
        )
        val spel  = Dominoes(dominoes)
        assertTrue(spel.bestaatKettingMet(1, 2))
        assertEquals("(1|4)-(4|2)", spel.bord())
    }

    @Test
    fun `ketting bestaat met één steen die je moet omdraaien`() {
        val dominoes = mutableListOf(
            Domino(1, 4),
        )
        val spel  = Dominoes(dominoes)
        assertTrue(spel.bestaatKettingMet(4, 1))
        assertEquals("(4|1)", spel.bord())
    }

    @Test
    fun `ketting bestaat met maar één steen en juiste ogen`() {
        val dominoes = mutableListOf(
            Domino(1, 4),
        )
        val spel  = Dominoes(dominoes)
        assertTrue(spel.bestaatKettingMet(1, 4))
        assertEquals("(1|4)", spel.bord())
    }

    @Test
    fun `ketting bestaat niet met maar één steen en niet juiste ogen`() {
        val dominoes = mutableListOf(
            Domino(1, 4),
        )
        val spel  = Dominoes(dominoes)
        assertFalse(spel.bestaatKettingMet(1, 1))
    }
}