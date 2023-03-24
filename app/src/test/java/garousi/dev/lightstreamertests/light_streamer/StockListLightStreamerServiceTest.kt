package garousi.dev.lightstreamertests.light_streamer

import com.lightstreamer.client.ItemUpdate
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.isActive
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class StockListLightStreamerServiceTest {

    private val connection: LightStreamerConnection = mockk(relaxed = true)

    // System under test
    private lateinit var service: StockListLightStreamerService

    @MockK
    private lateinit var itemUpdate: ItemUpdate

    private val testScope = TestScope()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        service = StockListLightStreamerService(externalScope = testScope, connection = connection)
    }

    @Test
    fun `subscribing to StockListLightStreamerService should start subscription on connection`() {

    }


    @Test
    fun `setFieldsToDto should update the DTO with the new data`() {
        val dto = StockListDto()
        every { itemUpdate.itemName } returns (StockListConstants.itemNames[0])
        every { itemUpdate.itemPos } returns (0)
        every { itemUpdate.getValue(StockListConstants.STOCK_NAME) } returns ("خساپا")
        every { itemUpdate.getValue(StockListConstants.PCT_CHANGE) } returns ("10.0")
        every { itemUpdate.getValue(StockListConstants.PCT_CHANGE) } returns ("10.0")
        every { itemUpdate.getValue(StockListConstants.BID_QUANTITY) } returns ("70.00")
        every { itemUpdate.getValue(StockListConstants.BID) } returns ("9.00")
        every { itemUpdate.getValue(StockListConstants.LAST_PRICE) } returns ("180.00")
        every { itemUpdate.getValue(StockListConstants.ASK) } returns ("1400.00")
        every { itemUpdate.getValue(StockListConstants.ASK_QUANTITY) } returns ("20.00")
        every { itemUpdate.getValue(StockListConstants.MIN) } returns ("4000.00")
        every { itemUpdate.getValue(StockListConstants.MAX) } returns ("1000.00")
        every { itemUpdate.getValue(StockListConstants.REF_PRICE) } returns ("1780.00")
        every { itemUpdate.getValue(StockListConstants.OPEN_PRICE) } returns ("1850.00")
        every { itemUpdate.getValue(StockListConstants.TIME) } returns ("1679647786")
        service.setFieldsToDto(itemUpdate)
        assert(service.dto.value != dto)
    }

    @Test
    fun `stream should emit DTO when setFieldsToDto is called`() = testScope.runTest {
        val dto = StockListDto()
        every { itemUpdate.itemName } returns (StockListConstants.itemNames[0])
        every { itemUpdate.itemPos } returns (0)
        every { itemUpdate.getValue(StockListConstants.STOCK_NAME) } returns ("خساپا")
        every { itemUpdate.getValue(StockListConstants.PCT_CHANGE) } returns ("10.0")
        every { itemUpdate.getValue(StockListConstants.PCT_CHANGE) } returns ("10.0")
        every { itemUpdate.getValue(StockListConstants.BID_QUANTITY) } returns ("70.00")
        every { itemUpdate.getValue(StockListConstants.BID) } returns ("9.00")
        every { itemUpdate.getValue(StockListConstants.LAST_PRICE) } returns ("180.00")
        every { itemUpdate.getValue(StockListConstants.ASK) } returns ("1400.00")
        every { itemUpdate.getValue(StockListConstants.ASK_QUANTITY) } returns ("20.00")
        every { itemUpdate.getValue(StockListConstants.MIN) } returns ("4000.00")
        every { itemUpdate.getValue(StockListConstants.MAX) } returns ("1000.00")
        every { itemUpdate.getValue(StockListConstants.REF_PRICE) } returns ("1780.00")
        every { itemUpdate.getValue(StockListConstants.OPEN_PRICE) } returns ("1850.00")
        every { itemUpdate.getValue(StockListConstants.TIME) } returns ("1679647786")
        service.setFieldsToDto(itemUpdate)
        val flow = flowOf(service.dto.value).take(1).toList()
        assert(flow[0] != dto)
    }

    @Test
    fun `unsubscribe should cancel the coroutine scope and unsubscribe the connection`() {
        service.unsubscribe()
        Assert.assertEquals(testScope.isActive, false)
    }

    @Test
    fun `subscribe() should call connection#subscribe()`() {
        val subscriptionMode = SubscriptionMode.MERGE
        val dataAdapter = "QUOTE_ADAPTER"
        val requestedSnapshot = "yes"
        val requestedMaxFrequency = "1"
        val itemNames = StockListConstants.itemNames
        val fieldNames = StockListConstants.fieldNames

        // mock the subscribe call on the connection object
        every { connection.subscribe(subscriptionMode, dataAdapter, requestedSnapshot, requestedMaxFrequency, itemNames, fieldNames) } returns mockk()

        // call the subscribe function on the service
        service.subscribe(subscriptionMode, dataAdapter, requestedSnapshot, requestedMaxFrequency, itemNames, fieldNames)

        // verify that the subscribe function on the connection object is called
        verify { connection.subscribe(subscriptionMode, dataAdapter, requestedSnapshot, requestedMaxFrequency, itemNames, fieldNames) }
    }

    @Test
    fun `subscribe() should call observeSubscriptionUpdates()`() {

    }

    @Test
    fun `setFieldsToDto() should update dto with ItemUpdate values`() {

    }
}