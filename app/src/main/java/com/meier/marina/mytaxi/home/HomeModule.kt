package com.meier.marina.mytaxi.home

import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext

val homeModule: Module = applicationContext {
    viewModel { HomeViewModel(get(), get()) }
}
