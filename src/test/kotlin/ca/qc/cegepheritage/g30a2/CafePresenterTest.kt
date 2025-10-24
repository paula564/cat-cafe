package ca.qc.cegepheritage.g30a2

import javafx.scene.control.ComboBox
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.util.Objects
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

        presenter.start()

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
        val presenter = CafePresenter(CafeModel(listOf(order1)), display)
        presenter.start()
        val order2 = Order(2, "Paula")

        presenter.onAddOrder(order2)
        display.lastAddedOrder = order1 to false

        assertEquals(listOf(order1), display.renderedQueue)
        assertEquals(1, display.count)
        assertEquals(order1, display.lastAddedOrder?.first)
        assertEquals(false, display.lastAddedOrder?.second)
        println(display.infos)
        assertEquals(true, display.infos.any { it.contains("That person is already in line!") })
    }

    @Test
    fun testOnServeWhenRemoveOrderShowInfoAndAppropriateMessage() {
        val display = FakeDisplay()
        val order1 = Order(1, "Paula")
        val order2 = Order(2, "Stella")
        val presenter = CafePresenter(CafeModel(listOf(order1, order2)), display)

        presenter.onServe()

        assertEquals(listOf(order2), display.renderedQueue)
        assertEquals(1, display.count)
        assertEquals(true, display.infos.any {it.contains("Served Paula!")})
    }

    @Test
    fun testOnServeWhenNoOrdersLeftShowAppropriateMessage() {
        val display = FakeDisplay()
        val presenter = CafePresenter(CafeModel(emptyList()), display)

        presenter.onServe()

        assertEquals(emptyList<Object>(), display.renderedQueue)
        assertEquals(0, display.count)
        assertEquals(true, display.infos.any {it.contains("No customers to serve!")})
    }

    @Test
    fun testOnServeWhenLastOrderIsServedQueueBecomesEmpty() {
        val display = FakeDisplay()
        val order1 = Order(1, "Paula")
        val presenter = CafePresenter(CafeModel(listOf(order1)), display)

        presenter.onServe()

        assertEquals(emptyList<Order>(), display.renderedQueue)
        assertEquals(0, display.count)
        assertEquals(true, display.infos.any {it.contains("Served Paula!")})
    }

    @Test
    fun testOnCloseRegisterRemovesAllOrdersAndShowsInfo() {
        val display = FakeDisplay()
        val order1 = Order(1, "Paula")
        val order2 = Order(2, "Stella")
        val presenter = CafePresenter(CafeModel(listOf(order1, order2)), display)

        presenter.onCloseRegister()

        assertEquals(emptyList<Order>(), display.renderedQueue)
        assertEquals(0, display.count)
        assertTrue(display.infos.any { it.contains("No orders waiting") })
    }

    @Test
    fun testOnCloseRegisterWhenQueueEmptyShowsInfoAndRemainsEmpty() {
        val display = FakeDisplay()
        val presenter = CafePresenter(CafeModel(emptyList()), display)

        presenter.onCloseRegister()

        assertEquals(emptyList<Order>(), display.renderedQueue)
        assertEquals(0, display.count)
        assertTrue(display.infos.any { it.contains("No orders waiting") })
    }

    @Test
    fun testOnCancelExistingIdShowsInfoAndRemovesOrder() {
        val display = FakeDisplay()
        val order1 = Order(1, "Paula")
        val presenter = CafePresenter(CafeModel(listOf(order1)), display)
        presenter.start()
        presenter.onCancel(order1.id)
        assertEquals(emptyList<Order>(), display.renderedQueue)
        assertEquals(0, display.count)
        assertTrue(display.infos.any { it.contains("Cancelled order for ${order1.customerName}") })
    }

    @Test
    fun testOnCancelNonExistingIdShowsInfoAndDoesNotRemoveOrder() {
        val display = FakeDisplay()
        val order1 = Order(1, "Paula")
        val presenter = CafePresenter(CafeModel(listOf(order1)), display)
        presenter.start()
        presenter.onCancel(4)
        assertEquals(listOf(order1), display.renderedQueue)
        assertEquals(1, display.count)
        assertTrue(display.infos.any { it.contains("No order found with ID 4") })
    }

    @Test
    fun testOnCancelEmptyListShowsInfoAndDoesNotRemoveOrder() {
        val display = FakeDisplay()
        val presenter = CafePresenter(CafeModel(emptyList()), display)
        presenter.start()
        presenter.onCancel(4)
        assertEquals(emptyList<Order>(), display.renderedQueue)
        assertEquals(0, display.count)
        assertTrue(display.infos.any { it.contains("No order found with ID 4") })
    }

    @Test
    fun testOnPrioritizeExistingIdShowsInfoAndPrioritizesOrder() {
        val display = FakeDisplay()
        val order1 = Order(1, "Paula")
        val order2 = Order(2, "Stella")
        val presenter = CafePresenter(CafeModel(listOf(order1, order2)), display)
        presenter.start()
        presenter.onPrioritize(order2.id)
        assertEquals(order2, display.renderedQueue.first())
        assertTrue(display.infos.any { it.contains("Prioritized order for ${order2.customerName}") })
    }

    @Test
    fun testOnPrioritizeNonExistingIdShowsInfoAndDoesNotRemoveOrder() {
        val display = FakeDisplay()
        val order1 = Order(1, "Paula")
        val order2 = Order(2, "Stella")
        val presenter = CafePresenter(CafeModel(listOf(order1, order2)), display)
        presenter.start()
        presenter.onPrioritize(4)
        assertEquals(order1, display.renderedQueue.first())
        assertTrue(display.infos.any { it.contains("No order found with ID 4") })
    }

    @Test
    fun testOnPrioritizeEmptyListShowsInfoAndDoesNotPrioritizeOrder() {
        val display = FakeDisplay()
        val presenter = CafePresenter(CafeModel(emptyList()), display)
        presenter.start()
        presenter.onPrioritize(4)
        assertEquals(0, display.count)
        assertTrue(display.infos.any { it.contains("No order found with ID 4") })
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
    override fun showAddedOrder(order: Order, wasAdded: Boolean) {
        lastAddedOrder = (order to wasAdded)
    }
    override fun showServedOrder(order: Order?) {lastServedOrder = order}
    override fun showNextInLine(order: Order?) {nextOrder = order}
    override fun showInfo(message: String) {infos = listOf(message)
    }
}