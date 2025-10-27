package ca.qc.cegepheritage.g30a2

import javafx.collections.FXCollections.observableList
import javafx.collections.ObservableList
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
//        if (order.customerName.isBlank() || (order.food == null && order.drink == null)) {
//            return null
//        } //ask kat about this
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

    fun <T> retrieveDropdownData (items : List<T>) : ObservableList<T> {
        return observableList(items)
    }

    fun closeRegister() {
        list.clear()
    }

    fun nextInLine() : Order? {
        return list.firstOrNull()
    }

    fun orderQueue(): List<Order> = list

    fun count(): Int = list.size
}