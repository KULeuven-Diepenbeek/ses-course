package be.kuleuven

import javafx.application.Application
import javafx.stage.Stage
import javafx.scene.Scene

import javafx.fxml.FXMLLoader

import javafx.scene.Parent

class JavaFXMain : Application() {
    override fun start(stage: Stage?) {
        val root = FXMLLoader.load<Parent>(javaClass.classLoader.getResource("scene.fxml"))

        val scene = Scene(root)

        stage!!.setTitle("Domino backtracking")
        stage.setScene(scene)
        stage.show()
    }
}

fun main() {
    Application.launch()
}
