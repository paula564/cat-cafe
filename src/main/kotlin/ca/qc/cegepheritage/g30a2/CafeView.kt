package ca.qc.cegepheritage.g30a2

import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.text.Font
import kotlin.random.Random

class CafeView : CafeDisplay {

    lateinit var presenter: CafePresenter
    val root: VBox
    val name : TextField
    private val queueDisplay = HBox(10.0).apply { alignment = Pos.CENTER }
    private val countLabel = Label("Students waiting: 0")
    private val infoLabel = Label("").apply {
        textFill = Color.DARKRED
        font = Font.font(14.0)
    }

//    init {
//        val trainPictureLabel = Label(trainPicture()).apply {
//            font = Font.font("Monospaced", 14.0)
//            textFill = Color.BROWN
//            alignment = Pos.CENTER
//        }

        val addOrderButton = Button("Add Order").apply {
            setOnAction {
                val order = Order()
                presenter.onAddPassenger(randomPassenger)
            }
            style = "-fx-background-color: #5F9EA0; -fx-text-fill: white;"
        }

        val boardPassengerButton = Button("Board Passenger").apply {
            setOnAction {
                //TODO: set what happens when board passenger button is clicked
                presenter.onBoardPassenger()

            }
            style = "-fx-background-color: #8B0000; -fx-text-fill: white;"
        }

        val peekButton = Button("Show Next in Line").apply {
            setOnAction { presenter.onPeekNextPassenger() }
            style = "-fx-background-color: #4682B4; -fx-text-fill: white;"
        }

        val clearButton =  Button("CLEAR").apply {
            setOnAction { presenter.onClear() }
            style = "-fx-background-color: #8B0900; -fx-text-fill: white;"
        }

        val buttons = HBox(10.0, addPassengerButton, boardPassengerButton, peekButton, clearButton).apply {
            alignment = Pos.CENTER
        }

        root = VBox(10.0, trainPictureLabel, buttons, queueDisplay, countLabel, infoLabel).apply {
            alignment = Pos.CENTER
            padding = Insets(20.0)
        }
    }

    override fun renderQueue(orders: List<Order>) {
        TODO("Not yet implemented")
    }

    override fun updateCount(count: Int) {
        TODO("Not yet implemented")
    }

    override fun showAddedOrder(order: Order, wasAdded: Boolean) {
        TODO("Not yet implemented")
    }

    override fun showServedOrder(order: String?) {
        TODO("Not yet implemented")
    }

    override fun showInfo(message: String) {
        TODO("Not yet implemented")
    }
}