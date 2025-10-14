package ca.qc.cegepheritage.g30a2

interface CafeDisplay {
    fun renderQueue(orders: List<Order>)
    fun updateCount(count: Int)
    fun showAddedOrder(order: Order, wasAdded: Boolean)
    fun showServedOrder(order : String?)
    fun showInfo(message: String)
}