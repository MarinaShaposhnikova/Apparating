package com.meier.marina.mytaxi.map

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLngBounds
import com.meier.marina.mytaxi.State

import com.meier.marina.mytaxi.utils.BaseScopeHandler
import com.meier.marina.mytaxi.utils.throttle
import com.meier.marina.network.repo.VehicleRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch

class MapViewModel(
    private val handler: BaseScopeHandler,
    private val repo: VehicleRepository
) : ViewModel() {

    private val channel: SendChannel<LatLngBounds>

    val liveData = MutableLiveData<State>()

    init {
        channel = Channel()
        handler.launch {
            channel.throttle().consumeEach {
                loadData(it)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        channel.close()
        handler.cancel()
    }

    fun onCoordinatesChange(latLngBounds: LatLngBounds) {
        handler.launch {
            try {
                channel.send(latLngBounds)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun loadData(latLngBounds: LatLngBounds) {
        val greater = latLngBounds.southwest
        val less = latLngBounds.northeast

        handler.launch {
            try {
                val list = repo.getListVehicle(
                    greater.latitude,
                    greater.longitude,
                    less.latitude,
                    less.longitude)
                liveData.postValue(State.Success(list))
            } catch (e: Exception) {
                e.printStackTrace()
                liveData.postValue(State.Error("Ooops, something went wrong"))
            }
        }
    }
}

