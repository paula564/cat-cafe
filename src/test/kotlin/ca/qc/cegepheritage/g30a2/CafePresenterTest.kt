package ca.qc.cegepheritage.g30a2

import javafx.scene.control.ComboBox
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.collections.any
import kotlin.text.contains

class CafePresenterTest {
    @Test
    fun testStartRendersInitialEmptyState() {
        val display = FakeDisplay()
        val presenter = CafePresenter(CafeModel(), display)

        presenter.start()

        assertEquals(emptyList<Order>(), display.renderedQueue)
        assertEquals(0, display.count)
    }

    @Test
    fun testOnAddOrderAddsAndUpdatesDisplay() {
        val display = FakeDisplay()
        val presenter = CafePresenter(CafeModel(), display)

        val order = Order(1, "Paula")
        presenter.onAddOrder(order)

        assertEquals(listOf(order), display.renderedQueue)
        assertEquals(1, display.count)
        assertEquals(order, display.lastAddedOrder?.first)
        assertEquals(true, display.lastAddedOrder?.second)
    }

    @Test
    fun testOnAddOrderDuplicateShowsInfoAndKeepsOrder() {
        val display = FakeDisplay()
        val order1 = Order(1, "Paula")
        val order2 = Order(2, "Paula")
        val presenter = CafePresenter(CafeModel(listOf(order1)), display)

        presenter.onAddOrder(order2)

        assertEquals(listOf(order1), display.renderedQueue)
        assertEquals(1, display.count)
        assertEquals(order1, display.lastAddedOrder?.first)
        assertEquals(false, display.lastAddedOrder?.second)
        assertEquals(true, display.infos.any { it.contains("That person is already in line!") })
    }
}


private class FakeDisplay : CafeDisplay {

    var renderedQueue: List<Order> = emptyList()
    var count: Int = 0
    var lastAddedOrder: Pair<Order, Boolean>? = null
    var lastServedOrder: Order? = null
    var nextOrder: Order? = null
    var infos: List<String> = emptyList()

    override var food: ComboBox<Food>? = null
    override var drinks: ComboBox<Drink>? = null

    override fun renderQueue(orders: List<Order>) {renderedQueue = orders}
    override fun updateCount(count: Int) {this.count = count}
    override fun showAddedOrder(order: Order, wasAdded: Boolean) {lastAddedOrder = order to wasAdded}
    override fun showServedOrder(order: Order?) {lastServedOrder = order}
    override fun showNextInLine(order: Order?) {nextOrder = order}
    override fun showInfo(message: String) {infos = listOf(message)
    }
}