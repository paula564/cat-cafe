package ca.qc.cegepheritage.g30a2
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test


class CafeModelTest {
    @Test
    fun testAddOrderCustomerAlreadyOrderedReturnsNullAndDoesNotAddCustomer() {
        val model = CafeModel()
        val order1 = Order(1, "Stella")
        val order2 = Order(2, "Stella")

        model.addOrder(order1)

        assertEquals(null, model.addOrder(order2), "addOrder() should return null for duplicates.")
        assertEquals(listOf(order1), model.orderQueue())
    }

    @Test
    fun testAddOrderCustomerNotYetOrderedReturnsOrderAndCorrectlyAddsCustomer() {
        val model = CafeModel()
        val order1 = Order(1, "Stella")

        assertEquals(order1, model.addOrder(order1), "addOrder() should return order1.")
        assertEquals(listOf(order1), model.orderQueue())
    }

    @Test
    fun testAddCustomerWithCustomersInQueueReturnsOrderAndCorrectlyAddsCustomer() {
        val model = CafeModel()
        val order1 = Order(1, "Stella")
        val order2 = Order(2, "Paula")
        val order3 = Order(3, "Kat")

        model.addOrder(order1)
        model.addOrder(order2)

        assertEquals(order3, model.addOrder(order3), "addOrder() should return order3.")
        assertEquals(listOf(order1, order2, order3), model.orderQueue())
    }

    @Test
    fun testServeRemoveCustomerFromEmptyListReturnsNullAndDoesNotRemoveCustomer() {
        val model = CafeModel()
        assertEquals(null, model.serve(), "serve() should return null since the list is empty.")
        assertEquals(emptyList<Order>(), model.orderQueue())
    }

    @Test
    fun testServeRemoveCustomerFromListWithOneOrderReturnsFirstOrderAndCorrectlyRemovesCustomer() {
        val model = CafeModel()
        val order1 = Order(1, "Stella")

        model.addOrder(order1)

        assertEquals(order1, model.serve(), "serve() should return order1.")
        assertEquals(emptyList<Order>(), model.orderQueue())
    }

    @Test
    fun testServeRemoveCustomerFromListWithMultipleOrdersReturnsFirstOrderAndCorrectlyRemovesCustomer() {
        val model = CafeModel()
        val order1 = Order(1, "Stella")
        val order2 = Order(2, "Paula")
        val order3 = Order(3, "Kat")

        model.addOrder(order1)
        model.addOrder(order2)
        model.addOrder(order3)

        assertEquals(order1, model.serve(), "serve() should return order1.")
        assertEquals(listOf(order2, order3), model.orderQueue())
    }

    @Test
    fun testCancelCustomerIdDoesNotExistDoesNotRemoveOrderAndReturnsNull() {
        val model = CafeModel()
        val order1 = Order(1, "Stella")
        val order2 = Order(2, "Paula")
        val order3 = Order(3, "Kat")

        model.addOrder(order1)
        model.addOrder(order2)
        model.addOrder(order3)

        assertEquals(null, model.cancel(4), "cancel() should return null without removing an order from the list.")
        assertEquals(listOf(order1, order2, order3), model.orderQueue())
    }

    @Test
    fun testCancelCustomerEmptyListDoesNotRemoveOrderAndReturnsNull() {
        val model = CafeModel()
        assertEquals(null, model.cancel(1), "cancel() should return null since the list is empty.")
        assertEquals(emptyList<Order>(), model.orderQueue())
    }

    @Test
    fun testCancelCustomerInListCorrectlyRemovesCustomerAndReturnsOrder() {
        val model = CafeModel()
        val order1 = Order(1, "Stella")
        val order2 = Order(2, "Paula")
        val order3 = Order(3, "Kat")

        model.addOrder(order1)
        model.addOrder(order2)
        model.addOrder(order3)

        assertEquals(order3, model.cancel(3), "cancel() should return order3")
        assertEquals(listOf(order1, order2), model.orderQueue())
    }

    @Test
    fun testPrioritizeCustomerIdDoesNotExistDoesNotPrioritizeOrderAndReturnsNull() {
        val model = CafeModel()
        val order1 = Order(1, "Stella")
        val order2 = Order(2, "Paula")
        val order3 = Order(3, "Kat")

        model.addOrder(order1)
        model.addOrder(order2)
        model.addOrder(order3)

        assertEquals(null, model.prioritize(4), "prioritize() should return null without prioritizing an order from the list.")
        assertEquals(listOf(order1, order2, order3), model.orderQueue())
    }

    @Test
    fun testPrioritizeCustomerEmptyListDoesNotPrioritizeOrderAndReturnsNull() {
        val model = CafeModel()
        assertEquals(null, model.prioritize(1), "prioritize() should return null since the list is empty.")
        assertEquals(emptyList<Order>(), model.orderQueue())
    }

    @Test
    fun testPrioritizeCustomerInListCorrectlyPrioritizesCustomerAndReturnsOrder() {
        val model = CafeModel()
        val order1 = Order(1, "Stella")
        val order2 = Order(2, "Paula")
        val order3 = Order(3, "Kat")

        model.addOrder(order1)
        model.addOrder(order2)
        model.addOrder(order3)

        assertEquals(order3, model.prioritize(3), "prioritize() should return order3")
        assertEquals(listOf(order3, order1, order2), model.orderQueue())
    }

    @Test
    fun testPopulateDropdownReturnsDropdownData() {
        val model = CafeModel()
        val items = listOf("Coffee", "Chai", "Matcha")

        model.populateDropdown(items)

        assertEquals(3, items.size)
        assertEquals(listOf("Coffee", "Chai", "Matcha"), items)
    }

    @Test
    fun testCloseRegisterClearsList() {
        val model = CafeModel()
        val order1 = Order(1, "Stella")
        val order2 = Order(2, "Paula")
        val order3 = Order(3, "Kat")

        model.addOrder(order1)
        model.addOrder(order2)
        model.addOrder(order3)
        model.closeRegister()

        assertEquals(emptyList<Order>(), model.orderQueue(), "List should be empty")
    }

    @Test
    fun testNextInLineEmptyListReturnsNull() {
        val model = CafeModel()
        assertEquals(null, model.nextInLine(), "Next in line should be empty")
    }

    @Test
    fun testNextInLineManyOrdersReturnsFirstOrder() {
        val model = CafeModel()
        val order1 = Order(1, "Stella")
        val order2 = Order(2, "Paula")
        val order3 = Order(3, "Kat")

        model.addOrder(order1)
        model.addOrder(order2)
        model.addOrder(order3)

        assertEquals(order1, model.nextInLine(), "Next in line should be order1")
    }

    @Test
    fun testOrderQueueQueueIsEmptyReturnsEmptyList() {
        val model = CafeModel()
        assertEquals(emptyList<Order>(), model.orderQueue(), "Count should be 0")
    }

    @Test
    fun testOrderQueueQueueHasManyItemsReturnsList() {
        val model = CafeModel()
        val order1 = Order(1, "Stella")
        val order2 = Order(2, "Paula")
        val order3 = Order(3, "Kat")

        model.addOrder(order1)
        model.addOrder(order2)
        model.addOrder(order3)

        assertEquals(listOf(order1, order2, order3), model.orderQueue(), "Order queue should have orders 1 to 3")
    }

    @Test
    fun testCountQueueIsEmptyReturns0() {
        val model = CafeModel()
        assertEquals(0, model.count(), "Count should be 0")
    }

    @Test
    fun testCountQueueHasManyItemsReturnsQueueSize() {
        val model = CafeModel()
        val order1 = Order(1, "Stella")
        val order2 = Order(2, "Paula")
        val order3 = Order(3, "Kat")

        model.addOrder(order1)
        model.addOrder(order2)
        model.addOrder(order3)

        assertEquals(3, model.count(), "Count should be 3")
    }









//    @Test
//    fun testAddCustomerWithOneItemOrderedReturnsOrderAndCorrectlyAddsCustomer() {
//
//    }

}