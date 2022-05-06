package be.kuleuven.view

import be.kuleuven.Domino
import javafx.scene.layout.Region

class DominoesStaatView(dominoes: List<Domino>) : Region() {

    init {
        var x = 0.0
        dominoes.forEach {
            val dominoView = DominoView(it)
            dominoView.layoutX = x
            children.add(dominoView)
            x += DominoView.WIDTH + 10
        }
    }

}