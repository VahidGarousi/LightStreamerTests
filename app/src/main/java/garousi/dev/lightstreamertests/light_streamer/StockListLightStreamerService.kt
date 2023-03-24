package garousi.dev.lightstreamertests.light_streamer

import com.lightstreamer.client.ItemUpdate
import com.lightstreamer.client.SubscriptionListener
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class StockListLightStreamerService constructor(
    private val externalScope: CoroutineScope,
    private val connection: LightStreamerConnection
) : LightStreamerService<StockListDto> {
    private val _dto: MutableStateFlow<StockListDto> = MutableStateFlow(StockListDto())
    override val dto: StateFlow<StockListDto> = _dto.asStateFlow()
    private val _stockListStream: MutableStateFlow<StockListDto> = MutableStateFlow(StockListDto())
    override val stream = _stockListStream.asStateFlow()

    init {
        connection.connect(
            serverAddress = StockListConstants.serverAddress,
            adapterSet = StockListConstants.adapterSet
        )
        println("")
    }

    override fun subscribe(vararg params: Any): LightStreamerService<StockListDto> {
        observeSubscriptionUpdates()
        connection.subscribe(
            subscriptionMode = SubscriptionMode.MERGE,
            itemNames = StockListConstants.itemNames,
            fieldNames = StockListConstants.fieldNames,
            dataAdapter = "QUOTE_ADAPTER",
            requestedSnapshot = "yes",
            requestedMaxFrequency = "1",
        )
        return this
    }

    override fun observeSubscriptionUpdates() {
        this.connection.subscriptionListener =
            object : SubscriptionListener by EmptySubscriptionListener {
                override fun onItemUpdate(itemUpdate: ItemUpdate) {
                    setFieldsToDto(itemUpdate)
                }
            }
    }

    override fun setFieldsToDto(itemUpdate: ItemUpdate) {
        val name = itemUpdate.getValue(StockListConstants.STOCK_NAME)
        val change = itemUpdate.getValue(StockListConstants.PCT_CHANGE)?.toDouble() ?: dto.value.change
        val bidSize = itemUpdate.getValue(StockListConstants.BID_QUANTITY)?.toDouble() ?: dto.value.bidSize
        val bid = itemUpdate.getValue(StockListConstants.BID)?.toDouble() ?: dto.value.bid
        val last = itemUpdate.getValue(StockListConstants.LAST_PRICE)?.toDouble() ?: dto.value.last
        val ask = itemUpdate.getValue(StockListConstants.ASK)?.toDouble() ?: dto.value.ask
        val askSize = itemUpdate.getValue(StockListConstants.ASK_QUANTITY)?.toDouble() ?: dto.value.askSize
        val min = itemUpdate.getValue(StockListConstants.MIN)?.toDouble() ?: dto.value.min
        val max = itemUpdate.getValue(StockListConstants.MAX)?.toDouble() ?: dto.value.max
        val ref = itemUpdate.getValue(StockListConstants.REF_PRICE)?.toDouble() ?: dto.value.ref
        val open = itemUpdate.getValue(StockListConstants.OPEN_PRICE)?.toDouble() ?: dto.value.open
        val stringTime = itemUpdate.getValue(StockListConstants.TIME)
        val time = LocalDateTime.ofInstant(Instant.ofEpochSecond(stringTime?.toLong() ?: Date().time), ZoneId.of("+03:30")) ?: dto.value.time
        val itemName = itemUpdate.itemName
        val itemPos = itemUpdate.itemPos
        _dto.update {
            it.copy(
                name = name,
                itemName = itemName,
                itemPos = itemPos,
                last = last,
                time = time,
                change = change,
                bidSize = bidSize,
                bid = bid,
                ask = ask,
                askSize = askSize,
                min = min,
                max = max,
                ref = ref,
                open = open,
            )
        }
        externalScope.launch(Dispatchers.IO) {
            _stockListStream.emit(dto.value)
        }
    }

    override fun unsubscribe() {
        connection.unsubscribe()
        externalScope.cancel()
    }
}