package be.kuleuven.view

import be.kuleuven.Domino
import javafx.scene.layout.Region
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle

class DominoView(val d: Domino) : Region() {

    companion object {
        val WIDTH = 100.0
        val HEIGHT = 50.0
    }

    init {
        val bounds = Rectangle(WIDTH, HEIGHT)
        bounds.fill = Color.WHITE
        val separator = Rectangle(HEIGHT, 5.0, 2.0, 40.0)
        separator.fill = Color.BLACK

        val eyeOne = DominoEyeView(d.een)
        val eyeTwo = DominoEyeView(d.twee)
        eyeTwo.layoutX = WIDTH / 2

        children.add(bounds)
        children.add(separator)
        children.addAll(eyeOne, eyeTwo)
    }
}