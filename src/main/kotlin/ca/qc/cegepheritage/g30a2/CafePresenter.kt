package ca.qc.cegepheritage.g30a2

import java.awt.Menu
import kotlin.text.clear

class CafePresenter (private val model: CafeModel, private val view: CafeDisplay) {

    val foodItems = Menu.Food.values().toList()
    val drinkItems = Menu.Drink.values().toList()

    fun start() {
        view.renderQueue(model.orderQueue())
        view.updateCount(model.count())
        populateDropdown(foodItems, view.food)
        populateDropdown(drinkItems, view.drinks)
    }
    fun onAddOrder(order: Order) {
        val added = model.addOrder(order)
        view.showAddedOrder(order, added)
        view.renderQueue(model.orderQueue())
        view.updateCount(model.count())

        if (!added) {
            view.showInfo("That person is already in line!")
        }
    }

    fun onServe() {
        val served = model.serve()
        view.showServedOrder(served)
        view.renderQueue(model.orderQueue())
        view.updateCount(model.count())
    }

    fun onCloseRegister() {
        model.closeRegister()
        view.renderQueue(model.orderQueue())
        view.updateCount(model.count())
        view.showInfo("No orders waiting")
    }
}