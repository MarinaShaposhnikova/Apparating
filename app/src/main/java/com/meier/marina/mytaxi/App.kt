package com.meier.marina.mytaxi

import android.app.Application
import com.meier.marina.mytaxi.home.homeModule
import com.meier.marina.mytaxi.map.mapModule
import com.meier.marina.mytaxi.utils.BaseScopeHandler
import com.meier.marina.network.networkModule
import org.koin.android.ext.android.startKoin
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(appModule, networkModule, homeModule, mapModule))
    }
}

val appModule: Module = applicationContext {
    factory { BaseScopeHandler() }
}
