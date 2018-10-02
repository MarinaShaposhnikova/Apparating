package com.meier.marina.mytaxi.map

import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext

val mapModule: Module = applicationContext {
    viewModel { MapViewModel(get(), get()) }
}
