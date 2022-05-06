package be.kuleuven

data class Domino(var een: Int, var twee: Int) {
    fun heeft(nr: Int): Boolean =
        een == nr || twee == nr
    fun flip() {
        val t = twee
        twee = een
        een = t
    }

    override fun toString(): String {
        return "(${een}|${twee})"
    }
}