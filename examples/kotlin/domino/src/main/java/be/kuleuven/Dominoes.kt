package be.kuleuven

class Dominoes(val ketting: MutableList<Domino>) {

    private val eindStaat: MutableList<Domino> = mutableListOf()
    fun staat(): MutableList<Domino> = eindStaat

    var step: (begin: Int, einde: Int) -> Unit = {een: Int, twee: Int -> }
    var gekozen: (steen: Domino) -> Unit = {steen: Domino -> }

    fun bord(): String {
        return eindStaat.joinToString("-") { it.toString() }
    }

    // See also: https://web.stanford.edu/class/archive/cs/cs106b/cs106b.1188/lectures/Lecture13/Lecture13.pdf
    fun bestaatKettingMet(begin: Int, einde: Int): Boolean {
        step(begin, einde)

        for (i in 0 until ketting.size) {
            val it = ketting.get(i)
            if(it.een != begin && it.twee == begin) it.flip()
            if(it.een == begin) {
                ketting.removeAt(i)     // KIES een mogelijkheid
                                        // VERKEN volgende mogelijkheden
                if(it.twee == einde || bestaatKettingMet(it.twee, einde)) {
                    // Indien gelukt: stop en maak keuze definitief
                    eindStaat.add(0, it)
                    gekozen(it)
                    return true
                }
                // Indien mislukt: zet steen terug en ga verder.
                ketting.add(i, it)
            }
        }
        return false
    }

}