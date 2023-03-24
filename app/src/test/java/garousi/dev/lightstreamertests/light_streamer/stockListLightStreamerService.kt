package garousi.dev.lightstreamertests.light_streamer

import com.lightstreamer.client.ClientListener
import com.lightstreamer.client.LightstreamerClient
import com.lightstreamer.client.Subscription


class MockLightStreamer(
    private val serverAddress: String = "http://test",
    private val adapterSet: String = "test"
) : LightstreamerClient(serverAddress, adapterSet) {

    var subscription: Subscription? = null

    override fun subscribe(subscription: Subscription) {
        this.subscription = subscription
    }

    override fun unsubscribe(subscription: Subscription) {
        this.subscription = null
    }

    override fun connect() {
        // Do nothing
    }

    override fun disconnect() {
        // Do nothing
    }

    override fun addListener(listener: ClientListener) {
        // Do nothing
    }

    override fun removeListener(listener: ClientListener) {
        // Do nothing
    }
}