package be.kuleuven.acting

class DieHardInADeepenBeek(var actor: IllBeBack) {

    fun recordActOne() {
        var succeeded = true
        for(i in 1..3) {
            succeeded = succeeded && actor.doBackFlip()
        }

        if(!succeeded)
            throw RuntimeException("do that again, please...")
    }
}
