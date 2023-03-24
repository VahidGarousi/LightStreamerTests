package garousi.dev.lightstreamertests.light_streamer

import java.time.LocalDateTime

data class StockListDto(
    val name: String? = null,
    val itemPos: Int? = null,
    val itemName: String? = null,
    val last: Double? = null,
    val time: LocalDateTime? = null,
    val change: Double? = null,
    val bidSize: Double? = null,
    val bid: Double? = null,
    val ask: Double? = null,
    val askSize: Double? = null,
    val min: Double? = null,
    val max: Double? = null,
    val ref: Double? = null,
    val open: Double? = null
)
