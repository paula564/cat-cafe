package ca.qc.cegepheritage.g30a2

import javafx.scene.control.ComboBox
import java.util.Collections.emptyList
import java.util.LinkedList

class CafeModel (initialOrders: List<Order> = emptyList()) {
    private val list = LinkedList<Order>()
    private var nextId = 1

    init {
        initialOrders.forEach { addOrder(it ) }
    }

    fun addOrder(order: Order): Order? {
        if (orderQueue().any { it.customerName == order.customerName }) {
            return null
        }
        val newOrder = order.copy(id = nextId++)
        list.add(newOrder)
        return newOrder
    }

    fun serve() : Order? {
        if (list.isNotEmpty()) {
            val order = list.removeFirst()
            return order
        } else {
            return null
        }
    }

    fun cancel(id : Int) : Order? {
        val order = list.find { it.id == id }
        if (order != null) {
            list.remove(order)
        }
        return order
    }

    fun prioritize(id : Int) : Order? {
        val order = list.find { it.id == id }
        if (order != null) {
            list.remove(order)
            list.addFirst(order)
        }
        return order
    }

    fun <T> populateDropdown (items : List<T>, dropdown: ComboBox<T>) {
        dropdown.items.clear()
        dropdown.items.addAll(items)
        dropdown.value = null
    }

    fun closeRegister() {
        list.clear()
    }

    fun nextInLine() : Order {
        return list.first()
    }

    fun orderQueue(): List<Order> = list.toList()

    fun count(): Int = list.size
}