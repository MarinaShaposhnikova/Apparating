package com.meier.marina.mytaxi.home

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.meier.marina.network.repo.VehicleRepository
import com.meier.marina.mytaxi.State
import com.meier.marina.mytaxi.utils.BaseScopeHandler
import kotlinx.coroutines.*

class HomeViewModel(
    private val handler: BaseScopeHandler,
    private val repo: VehicleRepository
) : ViewModel() {

    val liveData = MutableLiveData<State>()

    init {
        handler.launch {
            try {
                val list = repo.getListVehicle(p1Lat, p1Lon, p2Lat, p2Lon)
                liveData.postValue(State.Success(list))
            } catch (e: Exception) {
                e.printStackTrace()
                liveData.postValue(State.Error("Ooops, something went wrong"))
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        handler.cancel()
    }
}

const val p1Lat = 53.694865
const val p1Lon = 9.757589
const val p2Lat = 53.394655
const val p2Lon = 10.099891
