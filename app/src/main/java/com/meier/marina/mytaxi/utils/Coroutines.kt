package com.meier.marina.mytaxi.utils

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlin.coroutines.CoroutineContext

class BaseScopeHandler : CoroutineScope {

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Default

    fun cancel() {
        job.cancel()
    }
}

fun <T> Channel<T>.throttle(
    wait: Long = 1500
): ReceiveChannel<T> = GlobalScope.produce {

    var mostRecent: T

    consumeEach {
        mostRecent = it

        delay(wait)
        while (!isEmpty) {
            mostRecent = receive()

        }
        send(mostRecent)
    }
}
