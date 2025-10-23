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




class CafeView : CafeDisplay {

    lateinit var presenter: CafePresenter
    val root: VBox

    val name: TextField = TextField().apply {
        promptText = "Customer name"
        alignment = Pos.CENTER
        style = "-fx-background-color: white; -fx-text-fill: #D3D3D3; -fx-border-color: #FFDBBB; -fx-background-radius: 10px; -fx-border-radius: 10px; -fx-pref-width: 160;"
    }

    val idField = TextField().apply {
        promptText = "Order ID"
        style = "-fx-background-color: white; -fx-text-fill: #D3D3D3; -fx-border-color: #FFDBBB; -fx-background-radius: 10px; -fx-border-radius: 10px; -fx-max-width: 120;"
    }


    override var drinks: ComboBox<Drink> = ComboBox<Drink>().apply {
        promptText = "Select drink"
        style = "-fx-background-color: white; -fx-text-fill: #D3D3D3; -fx-border-color: #FFDBBB; -fx-background-radius: 10; -fx-border-radius: 10; -fx-max-width: 150px; -fx-max-height: 100px;"
    }
    override var food: ComboBox<Food> = ComboBox<Food>().apply {
        promptText = "Select food"
        style = "-fx-background-color: white; -fx-text-fill: #D3D3D3; -fx-border-color: #FFDBBB; -fx-background-radius:10; -fx-border-radius: 10; -fx-max-width: 150px; -fx-max-height: 100px;"
    }
    val queueDisplay = VBox(8.0).apply {
        alignment = Pos.TOP_CENTER
    }
    val countLabel = Label("Customers waiting: 0")


    val infoLabel = Label("").apply {
        textFill = Color.DARKRED
        font = Font.font(14.0)
    }

    val idLabel = Label("Order ID:")

        val addOrderButton = Button("Add Order").apply {
            setOnAction {

                val order = when {
                    food.getValue() !== null && drinks.getValue() !== null -> Order(0, name.text, food = food.getValue()!!, drink = drinks.getValue()!!)
                    food.getValue() !== null -> Order(0, name.text, food = food.getValue()!!)
                    drinks.getValue() !== null -> Order(0, name.text, drink = drinks.getValue()!!)
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
            font = Font.font("Arial", 28.0)
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
            style = "-fx-background-color: #FFD9C0; -fx-background-radius: 15; -fx-padding: 15; -fx-min-width: 230;"
        }

        val queueBox = VBox(10.0, queueTitle, queueDisplay).apply {
            alignment = Pos.TOP_CENTER
            style = "-fx-background-color: #FFD9C0; -fx-background-radius: 15; -fx-padding: 15; -fx-min-width: 300;"
        }

        val centralDisplay = HBox(30.0, buttonsBox, queueBox).apply {
            alignment = Pos.CENTER
            padding = Insets(15.0)
        }

        root = VBox(15.0, title, clearButton, addOrderRow, centralDisplay, countLabel, nextOrderLabel, infoLabel).apply {
            alignment = Pos.CENTER
            style = "-fx-background-color: #FFE3E2;"
            padding = Insets(25.0)
        }

    }

   override fun showNextInLine(order: Order?, orderQueue: List<Order>) {
        nextOrderLabel.text = if (order != null && orderQueue.isNotEmpty()) {
            "Next in line: ${order.customerName}"
        } else {
            "Next in line: none"
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
                    "-fx-background-color: white; -fx-padding: 10; -fx-border-color: #FFDBBB; -fx-border-radius: 10; -fx-background-radius: 10;"
            }
            queueDisplay.children.add(label)
        }
    }


    override fun updateCount(count: Int) {
            countLabel.text = "Customers waiting: $count"
        }

        override fun showAddedOrder(order: Order, wasAdded: Boolean) {
            if (wasAdded) {
                infoLabel.textFill = Color.DARKGREEN
                infoLabel.text = "Added order for ${order.customerName}"
            } else {
                infoLabel.textFill = Color.DARKRED
                infoLabel.text = "That person is already in line!"
            }
        }

    override fun showServedOrder(order: Order?) {
            if (order != null) {
                infoLabel.textFill = Color.DARKBLUE
                infoLabel.text = "Served ${order.customerName}!"
            } else {
                infoLabel.textFill = Color.DARKRED
                infoLabel.text = "No customers to serve!"
            }
        }

        override fun showInfo(message: String) {
            infoLabel.textFill = Color.DARKRED
            infoLabel.text = message
        }
    }



