package garousi.dev.lightstreamertests.light_streamer

import android.util.Log
import com.lightstreamer.client.ClientListener
import com.lightstreamer.client.LightstreamerClient
import com.lightstreamer.client.Subscription
import com.lightstreamer.client.SubscriptionListener


/**
 * This is for testing new way of using LightStreamer!
 */
class ProdLightStreamerConnection constructor(
) : LightStreamerConnection {
    override var lsClient: LightstreamerClient? = null
    override var subscription: Subscription? = null
    override var subscriptionListener: SubscriptionListener? = null

    override fun connect(serverAddress: String, adapterSet: String) {
        lsClient = LightstreamerClient(serverAddress, adapterSet)
        lsClient?.connect()
        lsClient?.addListener(object : ClientListener by EmptyClientListener {
            override fun onListenStart(client: LightstreamerClient) {
                Log.i("LOGGER", "")
            }

            override fun onServerError(errorCode: Int, errorMessage: String) {
                Log.i("LOGGER", "" + errorMessage + errorCode)
            }

            override fun onStatusChange(status: String) {
                Log.i("LOGGER", "" + status)
            }
        })
    }

    override fun disconnect() {
        // TODO: Check status, then disconnect!
        this.lsClient?.disconnect()
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
        // TODO: Check subscription status, then unsubscribe it!
        subscription?.let { this.lsClient?.unsubscribe(it) }
    }

    override fun subscribeToClient() {
        // TODO: Check that is this subscription activated and subscribed?
        subscription?.let { subscription ->
            subscriptionListener?.let { subscriptionListener ->
                subscription.addListener(subscriptionListener)
                lsClient?.subscribe(subscription)
            }
        }
    }

    override fun setDataAdapter(dataAdapter: String?) {
        if (dataAdapter != null && dataAdapter != "") {
            this.subscription?.dataAdapter = dataAdapter
        }
    }

    override fun setSubscription(
        subscriptionMode: SubscriptionMode,
        itemNames: Array<String>,
        fieldNames: Array<String>
    ) {
        subscription = Subscription(subscriptionMode.name, itemNames, fieldNames)
    }

    override fun setSubscription(
        subscriptionMode: SubscriptionMode,
        itemName: String,
        fieldNames: Array<String>
    ) {
        subscription = Subscription(subscriptionMode.name, itemName, fieldNames)
    }

    override fun setRequestedSnapshot(requestedSnapshot: String?) {
        if (requestedSnapshot != null && requestedSnapshot in listOf("yes", "no")) {
            this.subscription?.requestedSnapshot = requestedSnapshot
        } else {
            this.subscription?.requestedSnapshot = "no"
        }
    }

    override fun setRequestedMaxFrequency(requestedMaxFrequency: String?) {
        if (requestedMaxFrequency != null) {
            this.subscription?.requestedMaxFrequency = requestedMaxFrequency
        }
    }

}