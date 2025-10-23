package ca.qc.cegepheritage.g30a2

//import java.awt.Menu
import kotlin.text.clear

class CafePresenter (private val model: CafeModel, private val view: CafeDisplay) {

    val foodItems = Food.entries
    val drinkItems = Drink.entries

    fun start() {
        view.renderQueue(model.orderQueue())
        view.updateCount(model.count())
        model.populateDropdown(foodItems, view.food)
        model.populateDropdown(drinkItems, view.drinks)
        view.showNextInLine(model.nextInLine()) //maybe?? idkkk
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
        view.showServedOrder(served)
        view.renderQueue(model.orderQueue())
        view.updateCount(model.count())
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