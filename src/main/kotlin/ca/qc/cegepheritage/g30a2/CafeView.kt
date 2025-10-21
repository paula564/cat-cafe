package ca.qc.cegepheritage.g30a2

import javafx.collections.ObservableList
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.ComboBox
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.text.Font
import kotlin.random.Random
import javafx.collections.FXCollections
import java.awt.Menu


class CafeView : CafeDisplay {

    lateinit var presenter: CafePresenter
    val root: VBox
    val name: TextField
    val food : ComboBox<String>
    val drinks : ComboBox<String>
    private val queueDisplay = HBox(10.0).apply { alignment = Pos.CENTER }
    private val countLabel = Label("Customers waiting: 0")


    private val infoLabel = Label("").apply {
        textFill = Color.DARKRED
        font = Font.font(14.0)
    }

        val addOrderButton = Button("Add Order").apply {
            setOnAction {
                val order = Order(1, name.text, food = food.getValue() as Food)
                presenter.onAddOrder(order)
            }
            style = "-fx-background-color: #DE3163; -fx-text-fill: white;"
        }

        val serveButton = Button("Serve").apply {
            setOnAction {
                presenter.onServe()

            }
            style = "-fx-background-color: #DE3163; -fx-text-fill: white;"
        }

        val clearButton = Button("CLEAR").apply {
            setOnAction { presenter.onCloseRegister() }
            style = "-fx-background-color: #DE3163; -fx-text-fill: white;"
        }

        val buttons = HBox(10.0, addOrderButton, serveButton, clearButton).apply {
            alignment = Pos.CENTER
        }




    init {

        name = TextField("").apply {
            promptText = "Customer name"
            alignment = Pos.CENTER
            padding = Insets(20.0)
            style = "-fx-background-color: #DE3163; -fx-text-fill: white;"
        }

        food = ComboBox<Menu.Food>().apply {
            padding = Insets(25.0)
            style = "-fx-background-color: #DE3163; -fx-text-fill: white;"
        }

        drinks = ComboBox<String>(drinkList).apply {
            padding = Insets(29.0)
            style = "-fx-background-color: #DE3163; -fx-text-fill: white;"
        }

        root = VBox(10.0, buttons, queueDisplay, countLabel, infoLabel, name, food, drinks).apply {
            alignment = Pos.CENTER
            padding = Insets(20.0)
        }

    }

        override fun renderQueue(orders: List<Order>) {
            1+2
        }

        override fun updateCount(count: Int) {
            1+2
        }

        override fun showAddedOrder(order: Order, wasAdded: Boolean) {
            1+2
        }

        override fun showServedOrder(order: String?) {
            1+2
        }

        override fun showInfo(message: String) {
            1+2
        }
    }



