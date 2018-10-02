package com.meier.marina.mytaxi

import com.meier.marina.network.entity.Vehicle

sealed class State {
    class Success(val list: List<Vehicle>) : State()
    class Error(val message: String) : State()
}
