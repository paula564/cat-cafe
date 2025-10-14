package ca.qc.cegepheritage.g30a2

import java.util.LinkedList

class CafeModel (initialOrders: List<Order> = emptyList()) {
    private val list = LinkedList<Order>()

    init {
        initialOrders.forEach { addOrder(it ) }
    }

    fun addOrder(order: Order) : Boolean {
        if (list.contains(order)) {
            return false
        } else {
            list.addLast(order)
            return true
        }
    }

    fun serve() : String {
        if (list.isNotEmpty()) {
            val order = list.removeFirst()
            return "$order has been served!"
        } else {
            return "No orders left to serve!"
        }
    }

    fun closeRegister() {
        list.clear()
    }

    fun orderQueue(): List<Order> = list.toList()

    fun count(): Int = list.size
}