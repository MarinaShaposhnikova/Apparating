package com.meier.marina.network

import com.meier.marina.network.entity.Response
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("/")
    fun getListVehicle(@Query("p1Lat") p1Lat: Double,
                       @Query("p1Lon") p1Lon: Double,
                       @Query("p2Lat") p2Lat: Double,
                       @Query("p2Lon") p2Lon: Double): Deferred<Response>
}
