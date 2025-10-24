package ca.qc.cegepheritage.g30a2

//import java.awt.Menu
import javafx.collections.ObservableList
import kotlin.text.clear

class CafePresenter (private val model: CafeModel, private val view: CafeDisplay) {

    fun start() {
        val foodItems = Food.entries.toList()
        val drinkItems = Drink.entries.toList()
        view.renderQueue(model.orderQueue())
        view.updateCount(model.count())
        view.food?.items = model.populateDropdown(foodItems)
        view.food?.items?.addFirst(null)
        view.drinks?.items = model.populateDropdown(drinkItems)
        view.drinks?.items?.addFirst(null)
        view.showNextInLine(model.nextInLine())
    }
    fun onAddOrder(order: Order) {
        val addedOrder = model.addOrder(order)
        if (addedOrder != null) {
            view.showAddedOrder(addedOrder, true)
            view.renderQueue(model.orderQueue())
            view.updateCount(model.count())
            view.showInfo("Order added!")
        } else {
            view.showInfo("That person is already in line!")
        }
        view.showNextInLine(model.nextInLine())
    }

    fun onServe() {
        val served = model.serve()
        if (served != null) {
            view.showServedOrder(served)
            view.renderQueue(model.orderQueue())
            view.updateCount(model.count())
            view.showNextInLine(model.nextInLine())
            view.showInfo("Served ${served.customerName}!")
        } else {
            view.showInfo("No customers to serve!")
        }
        view.showNextInLine(model.nextInLine())
    }

    fun onCloseRegister() {
        model.closeRegister()
        view.renderQueue(model.orderQueue())
        view.updateCount(model.count())
        view.showInfo("No orders waiting")
        view.showNextInLine(model.nextInLine())
    }

    fun onCancel(id: Int) {
        val removed = model.cancel(id)
        if (removed != null) {
            view.showInfo("Cancelled order for ${removed.customerName}")
            view.renderQueue(model.orderQueue())
            view.updateCount(model.count())
        } else {
            view.showInfo("No order found with ID $id")
        }
        view.showNextInLine(model.nextInLine())
    }

    fun onPrioritize(id: Int) {
        val prioritized = model.prioritize(id)
        if (prioritized != null) {
            view.showInfo("Prioritized order for ${prioritized.customerName}")
            view.renderQueue(model.orderQueue())
            view.updateCount(model.count())
        } else {
            view.showInfo("No order found with ID $id")
        }
        view.showNextInLine(model.nextInLine())
    }
}




//add so next order is visible