package com.meier.marina.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.meier.marina.network.repo.VehicleRepository
import com.meier.marina.network.repo.VehicleRepositoryBackend
import okhttp3.OkHttpClient
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule: Module = applicationContext {
    bean { gson() }
    bean { okhhtp() }
    bean { retrofit(get(), get(), BASE_URL) }
    bean { api(get()) }
    bean { VehicleRepositoryBackend(get()) as VehicleRepository }
}

internal const val BASE_URL = "https://fake-poi-api.mytaxi.com"

internal fun api(retrofit: Retrofit): Api = retrofit.create(Api::class.java)

internal fun retrofit(gson: Gson, okHttpClient: OkHttpClient, baseUrl: String): Retrofit {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .build()
}

internal fun gson(): Gson = GsonBuilder().create()

internal fun okhhtp(): OkHttpClient {
    return OkHttpClient.Builder()
        .build()
}
