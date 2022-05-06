package be.kuleuven.view

import javafx.scene.layout.Region
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import java.lang.UnsupportedOperationException

class DominoEyeView(aantal: Int) : Region() {

    init {
        when(aantal) {
            1 -> one()
            2 -> two()
            3 -> three()
            4 -> four()
            5 -> five()
            6 -> six()
            else -> {
                throw UnsupportedOperationException("Domino moet 1 tot 6 ogen hebben")
            }
        }
    }

    private fun oog() = Circle(15.0, 15.0, 6.0, Color.BLACK)

    private fun one() {
        val oog = oog()
        oog.centerX += 8
        oog.centerY += 8
        children.add(oog)
    }
    private fun two() {
        val een = oog()
        val twee = oog()
        twee.centerX += 17
        twee.centerY += 17
        children.addAll(een, twee)
    }

    private fun three() {
        val een = oog()
        val twee = oog()
        val drie = oog()
        twee.centerX += 8
        twee.centerY += 8
        drie.centerX += 17
        drie.centerY += 17
        children.addAll(een, twee, drie)
    }
    private fun four() {
        val een = oog()
        val twee = oog()
        val drie = oog()
        val vier = oog()
        twee.centerX += 17
        drie.centerY += 17
        vier.centerX += 17
        vier.centerY += 17
        children.addAll(een, twee, drie, vier)
    }
    private fun five() {
        val een = oog()
        val twee = oog()
        val drie = oog()
        val vier = oog()
        val vijf = oog()
        twee.centerX += 17
        drie.centerY += 17
        vier.centerX += 17
        vier.centerY += 17
        vijf.centerX += 7
        vijf.centerY += 7
        children.addAll(een, twee, drie, vier, vijf)
    }
    private fun six() {
        val een = oog()
        val twee = oog()
        val drie = oog()
        val vier = oog()
        val vijf = oog()
        val zes = oog()
        twee.centerX += 10
        drie.centerX += 20
        vier.centerY += 20
        vijf.centerX += 10
        vijf.centerY += 20
        zes.centerX += 20
        zes.centerY += 20
        children.addAll(een, twee, drie, vier, vijf, zes)
    }
}