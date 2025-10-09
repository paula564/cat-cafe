package ca.qc.cegepheritage.g30a2

import javafx.application.Application
import javafx.stage.Stage

class CafeApplication : Application() {
    override fun start(stage: Stage) {
        stage.title = "Kat's Cat Cafe\uD83D\uDE38"
        stage.show()
    }
}

fun main() {
    Application.launch(CafeApplication::class.java)
}