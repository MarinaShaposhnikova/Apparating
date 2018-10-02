package com.meier.marina.mytaxi

import android.app.Application
import com.meier.marina.mytaxi.home.homeModule
import com.meier.marina.mytaxi.map.mapModule
import com.meier.marina.network.networkModule
import org.koin.android.ext.android.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(networkModule, homeModule, mapModule))
    }
}
