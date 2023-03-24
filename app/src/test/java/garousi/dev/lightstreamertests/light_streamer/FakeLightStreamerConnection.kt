package garousi.dev.lightstreamertests.light_streamer

import com.lightstreamer.client.LightstreamerClient
import com.lightstreamer.client.Subscription
import com.lightstreamer.client.SubscriptionListener
import io.mockk.mockk

class FakeLightStreamerConnection : LightStreamerConnection {
    override val lsClient: LightstreamerClient = mockk(relaxUnitFun = true)
    override var subscriptionListener: SubscriptionListener? = mockk(relaxUnitFun = true)
    override var subscription: Subscription? = mockk(relaxUnitFun = true)

    override fun connect(serverAddress: String, adapterSet: String) {
        // no op
    }

    override fun disconnect() {
        // no op
    }

    override fun subscribe(
        subscriptionMode: SubscriptionMode,
        dataAdapter: String?,
        requestedSnapshot: String?,
        requestedMaxFrequency: String?,
        itemNames: Array<String>,
        fieldNames: Array<String>
    ): Subscription? {
        this.setSubscription(subscriptionMode, itemNames, fieldNames)
        this.setDataAdapter(dataAdapter)
        this.setRequestedSnapshot(requestedSnapshot)
        this.setRequestedMaxFrequency(requestedMaxFrequency)
        this.subscribeToClient()
        return subscription
    }

    override fun subscribe(
        subscriptionMode: SubscriptionMode,
        dataAdapter: String?,
        requestedSnapshot: String?,
        requestedMaxFrequency: String?,
        itemName: String,
        fieldNames: Array<String>
    ): Subscription? {
        this.setSubscription(subscriptionMode, itemName, fieldNames)
        this.setDataAdapter(dataAdapter)
        this.setRequestedSnapshot(requestedSnapshot)
        this.setRequestedMaxFrequency(requestedMaxFrequency)
        this.subscribeToClient()
        return subscription
    }

    override fun unsubscribe() {
        // no op
    }

    override fun subscribeToClient() {
        subscriptionListener?.onSubscription()
    }

    override fun setSubscription(subscriptionMode: SubscriptionMode, itemNames: Array<String>, fieldNames: Array<String>) {
        subscription = mockk(relaxed = true)
    }

    override fun setSubscription(subscriptionMode: SubscriptionMode, itemName: String, fieldNames: Array<String>) {
        subscription = mockk(relaxed = true)
    }

    override fun setRequestedSnapshot(requestedSnapshot: String?) {
        // no op
    }

    override fun setRequestedMaxFrequency(requestedMaxFrequency: String?) {
        // no op
    }


    override fun setDataAdapter(dataAdapter: String?) {
        // no op
    }
}