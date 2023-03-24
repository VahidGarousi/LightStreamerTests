package garousi.dev.lightstreamertests.light_streamer

object StockListConstants {
    const val serverAddress = "https://push.lightstreamer.com:443"
    const val adapterSet = "DEMO"

    internal const val LAST_PRICE = "last_price"
    internal const val TIME = "time"
    internal const val PCT_CHANGE = "pct_change"
    internal const val BID_QUANTITY = "bid_quantity"
    internal const val BID = "bid"
    internal const val ASK = "ask"
    internal const val ASK_QUANTITY = "ask_quantity"
    internal const val MIN = "min"
    internal const val MAX = "max"
    internal const val REF_PRICE = "ref_price"
    internal const val OPEN_PRICE = "open_price"
    internal const val STOCK_NAME = "stock_name"
    internal const val ITEM_STATUS = "item_status"
    val itemNames = arrayOf(
        "item1",
        "item2",
        "item3",
        "item4",
        "item5",
        "item6",
        "item7",
        "item8",
        "item9",
        "item10",
        "item11",
        "item12",
        "item13",
        "item14",
        "item15",
    )
    val fieldNames = arrayOf(
        LAST_PRICE,
        TIME,
        PCT_CHANGE,
        BID_QUANTITY,
        BID,
        ASK,
        ASK_QUANTITY,
        MIN,
        MAX,
        REF_PRICE,
        OPEN_PRICE,
        STOCK_NAME,
        ITEM_STATUS,
    )
}