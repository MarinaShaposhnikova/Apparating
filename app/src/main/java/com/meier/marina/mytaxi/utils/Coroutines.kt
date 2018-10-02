package com.meier.marina.mytaxi.utils

import kotlinx.coroutines.DefaultDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.newSingleThreadContext
import kotlin.coroutines.CoroutineContext

private val parallelism by lazy {
    (Runtime.getRuntime().availableProcessors() - 1).coerceAtLeast(1)
}

val BG by lazy {
    if (parallelism > 1) DefaultDispatcher
    else newSingleThreadContext("BgThread")
}

fun <T> Channel<T>.throttle(
    wait: Long = 1500,
    context: CoroutineContext = DefaultDispatcher
): ReceiveChannel<T> = produce(context) {

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
