package garousi.dev.lightstreamertests.light_streamer

import com.lightstreamer.client.ItemUpdate
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * This is for testing new way of using LightStreamer!
 */
interface LightStreamerService<T> {
    val dto: StateFlow<T>
    val stream: SharedFlow<T>
    fun subscribe(vararg params: Any): LightStreamerService<T>
    fun unsubscribe()
    fun observeSubscriptionUpdates()
    fun setFieldsToDto(itemUpdate: ItemUpdate)
}