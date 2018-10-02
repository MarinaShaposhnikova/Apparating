package com.meier.marina.mytaxi.map

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLngBounds
import com.meier.marina.mytaxi.State

import com.meier.marina.mytaxi.utils.BG
import com.meier.marina.mytaxi.utils.throttle
import com.meier.marina.network.repo.VehicleRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch

class MapViewModel(
    private val repo: VehicleRepository
) : ViewModel() {

    private val channel: SendChannel<LatLngBounds>

    val liveData = MutableLiveData<State>()

    init {
        channel = Channel()
        GlobalScope.launch(BG) {
            channel.throttle(context = BG).consumeEach {
                loadData(it)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        channel.close()
    }

    fun onCoordinatesChange(latLngBounds: LatLngBounds) {
        GlobalScope.launch(BG) {
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

        GlobalScope.launch(BG) {
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
