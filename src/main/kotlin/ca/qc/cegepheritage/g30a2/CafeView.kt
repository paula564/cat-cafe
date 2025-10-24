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
import javafx.scene.text.FontWeight


class CafeView : CafeDisplay {

    lateinit var presenter: CafePresenter
    val root: VBox

    val name: TextField = TextField().apply {
        promptText = "Customer name"
        alignment = Pos.CENTER
        style = "-fx-background-color: white; -fx-text-fill: #D3D3D3; -fx-border-color: #53131E; -fx-background-radius: 10px; -fx-border-radius: 10px; -fx-pref-width: 160px;"
    }

    val idField = TextField().apply {
        promptText = "Order ID"
        style = "-fx-background-color: white; -fx-text-fill: #D3D3D3; -fx-border-color: #53131E; -fx-background-radius: 10px; -fx-border-radius: 10px; -fx-max-width: 120px;"
    }

    override var drinks: ComboBox<Drink>? = ComboBox<Drink>().apply {
        promptText = "Select drink"
        style = "-fx-background-color: white; -fx-text-fill: #D3D3D3; -fx-border-color: #53131E; -fx-background-radius: 10px; -fx-border-radius: 10px; -fx-max-width: 150px; -fx-max-height: 100px;"
    }
    override var food: ComboBox<Food>? = ComboBox<Food>().apply {
        promptText = "Select food"
        style = "-fx-background-color: white; -fx-text-fill: #D3D3D3; -fx-border-color: #53131E; -fx-background-radius:10px; -fx-border-radius: 10px; -fx-max-width: 150px; -fx-max-height: 100px;"
    }
    val queueDisplay = VBox(8.0).apply {
        alignment = Pos.TOP_CENTER
    }
    val countLabel = Label("Customers waiting: 0")

    val infoLabel = Label("").apply {
        font = Font.font(14.0)
    }

    val idLabel = Label("Order ID:")

        val addOrderButton = Button("Add Order").apply {
            setOnAction {

                val order = when {
                    food?.getValue() !== null && drinks?.getValue() !== null -> Order(0, name.text, food = food?.getValue()!!, drink = drinks?.getValue()!!)
                    food?.getValue() !== null -> Order(0, name.text, food = food?.getValue()!!)
                    drinks?.getValue() !== null -> Order(0, name.text, drink = drinks?.getValue()!!)
                    else -> Order(0, name.text)
                }
                if (order.food !== null || order.drink !== null) {
                    presenter.onAddOrder(order)
                }
            }
            style = "-fx-background-color: #FFAE42 ; -fx-text-fill: white;"
        }

        val nextOrderLabel = Label("Next in line: none")

        val serveButton = Button("Serve").apply {
            setOnAction {
                presenter.onServe()

            }
            style = "-fx-background-color: #58CC02; -fx-text-fill: white;"
        }

        val clearButton = Button("CLOSE REGISTER").apply {
            setOnAction { presenter.onCloseRegister() }
            style = "-fx-background-color: #B22222; -fx-text-fill: white;"
        }

    val cancelButton = Button("Cancel").apply {
        style = "-fx-background-color: #DE3163; -fx-text-fill: white;"
        setOnAction {
            val id = idField.text.toIntOrNull()
            if (id != null) {
                presenter.onCancel(id)
            }
        }
    }

    val prioritizeButton = Button("Prioritize").apply {
        style = "-fx-background-color: #FFA500; -fx-text-fill: white;"
        setOnAction {
            val id = idField.text.toIntOrNull()
            if (id != null) {
                presenter.onPrioritize(id)
            }
        }
    }

    init {

        val title = Label("Kat's Cat Cafe!").apply {
            font = Font.font("Arial", FontWeight.BOLD,  28.0)
        }

        val queueTitle = Label("Queue").apply {
            alignment = Pos.TOP_CENTER
            font = Font.font("Arial", 18.0)
        }

        val addOrderRow = HBox(10.0, name, drinks, food, addOrderButton).apply {
            alignment = Pos.CENTER
            padding = Insets(5.0)
        }

        val buttonsBox = VBox(10.0, serveButton, idLabel, idField, cancelButton, prioritizeButton).apply {
            alignment = Pos.CENTER
            style = "-fx-background-color: #FEE1C7; -fx-background-radius: 15px; -fx-padding: 15px; -fx-min-width: 230px;"
        }

        val queueBox = VBox(10.0, queueTitle, queueDisplay).apply {
            alignment = Pos.TOP_CENTER
            style = "-fx-background-color: #FEE1C7; -fx-background-radius: 15px; -fx-padding: 15px; -fx-min-width: 300px;"
        }

        val centralDisplay = HBox(30.0, buttonsBox, queueBox).apply {
            alignment = Pos.CENTER
            padding = Insets(15.0)
        }

        root = VBox(15.0, title, clearButton, addOrderRow, centralDisplay, countLabel, nextOrderLabel, infoLabel).apply {
            alignment = Pos.CENTER
            style = "-fx-background-color: #9999C3;"
            padding = Insets(25.0)
        }

    }

   override fun showNextInLine(order: Order?) {
        nextOrderLabel.text = if (order != null) {
            "Next in line: ${order.customerName}"
        } else {
            "No one is in line!"
        }
    }

    override fun renderQueue(orders: List<Order>) {
        queueDisplay.children.clear()
        for (order in orders) {
            val label = when {
                order.drink != null && order.food != null -> Label("#${order.id} - ${order.customerName}: ${order.food} & ${order.drink}")
                order.food != null -> Label("#${order.id} - ${order.customerName}: ${order.food}")
                order.drink != null -> Label("#${order.id} - ${order.customerName}: ${order.drink}")
                else -> {
                    infoLabel.textFill = Color.DARKRED
                    infoLabel.text = "Please enter a valid order."
                    Label("#${order.id} - ${order.customerName}")
                }
            }.apply {
                style =
                    "-fx-background-color: white; -fx-padding: 10px; -fx-border-color: #53131E; -fx-border-radius: 10px; -fx-background-radius: 10px;"
            }
            queueDisplay.children.add(label)
        }
    }


    override fun updateCount(count: Int) {
            countLabel.text = "Customers waiting: $count"
        }

        override fun showAddedOrder(order: Order, wasAdded: Boolean) {
            if (wasAdded) {
                infoLabel.text = "Added order for ${order.customerName}"
            } else {
                infoLabel.text = "That person is already in line!"
            }
        }

    override fun showServedOrder(order: Order?) {
            if (order != null) {
                infoLabel.text = "Served ${order.customerName}!"
            } else {
                infoLabel.text = "No customers to serve!"
            }
        }

        override fun showInfo(message: String) {
            infoLabel.text = message
        }
    }



