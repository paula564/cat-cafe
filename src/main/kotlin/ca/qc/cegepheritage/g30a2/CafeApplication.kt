package ca.qc.cegepheritage.g30a2

import javafx.application.Application
import javafx.scene.Scene
import javafx.stage.Stage

class CafeApplication : Application() {
    companion object {
        private const val WINDOW_WIDTH = 600.0
        private const val WINDOW_HEIGHT = 500.0
    }

    override fun start(primaryStage: Stage) {

        val model = CafeModel()
        val view = CafeView()
        val presenter = CafePresenter(model, view)

        view.presenter = presenter

        primaryStage.scene = Scene(view.root, WINDOW_WIDTH, WINDOW_HEIGHT)
        primaryStage.title = "Kat's Cat Cafe\uD83D\uDE38"
        primaryStage.show()

        presenter.start()
    }
}

fun main() {
    Application.launch(CafeApplication::class.java)
}

//need to fix next person in line display