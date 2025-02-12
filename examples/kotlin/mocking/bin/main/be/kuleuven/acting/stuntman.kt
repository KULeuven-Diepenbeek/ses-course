package be.kuleuven.acting

interface IllBeBack {
    fun doBackFlip(): Boolean
}

class ArnieLookalike : IllBeBack {
    override fun doBackFlip(): Boolean = false
}

class StuntmanArnie : IllBeBack {
    override fun doBackFlip(): Boolean = true
}
