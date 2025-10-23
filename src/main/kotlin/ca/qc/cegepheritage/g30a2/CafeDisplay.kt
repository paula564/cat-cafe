package ca.qc.cegepheritage.g30a2

import javafx.scene.control.ComboBox

interface CafeDisplay {
    var food: ComboBox<Food>
    var drinks: ComboBox<Drink>
    fun renderQueue(orders: List<Order>)
    fun updateCount(count: Int)
    fun showAddedOrder(order: Order, wasAdded: Boolean)
    fun showServedOrder(order: Order?)
    fun showNextInLine(order: Order?, orderQueue: List<Order>)
    fun showInfo(message: String)
}