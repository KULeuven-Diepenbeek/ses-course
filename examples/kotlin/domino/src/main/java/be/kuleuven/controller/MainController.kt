package be.kuleuven.controller

import be.kuleuven.Domino
import be.kuleuven.Dominoes
import be.kuleuven.view.DominoView
import be.kuleuven.view.DominoesStaatView
import javafx.application.Platform
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.layout.AnchorPane


class MainController {

    @FXML
    private lateinit var button: Button

    @FXML
    private lateinit var text: TextField

    @FXML
    private lateinit var txtBegin: TextField

    @FXML
    private lateinit var txtEinde: TextField

    @FXML
    private lateinit var spelVeld: AnchorPane

    @FXML
    private lateinit var uitkomst: Label

    private var dominoStateY = 0.0

    fun initialize() {
        button.setOnAction { e -> clickedOnButton(e) }
    }

    private fun clickedOnButton(e: ActionEvent) {
        resetSpelVeld()

        val stenen = leesDominoInput()
        val spel = Dominoes(stenen.toMutableList())
        spel.gekozen = {
            // Alle UI update logica moet op de JavaFX UI thread gebeuren.
            Platform.runLater {
                val state = DominoesStaatView(spel.staat())
                state.layoutY += dominoStateY
                spelVeld.children.add(state)
                dominoStateY += DominoView.HEIGHT + 10
            }
            // Om stap per stap te werken moet hieronder een nieuwe thread worden aangemaakt
            // Anders is Thread.currentThread() de JavaFX UI thread die we niet willen stoppen.
            Thread.sleep(1000)
        }

        Thread {
            val mogelijk = spel.bestaatKettingMet(txtBegin.text.toInt(), txtEinde.text.toInt())
            Platform.runLater {
                uitkomst.text = "Mogelijk? ${mogelijk}"
            }
        }.start()
    }

    private fun resetSpelVeld() {
        spelVeld.children.clear()
        dominoStateY = 0.0
    }

    private fun leesDominoInput() = text.text.split("-").map {
        val nrs = it.replace("(", "").replace(")", "").split("|").map { it.toInt() }
        Domino(nrs[0], nrs[1])
    }
}