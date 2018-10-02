package com.meier.marina.network.repo

import com.meier.marina.network.Api
import com.meier.marina.network.entity.Vehicle

internal class VehicleRepositoryBackend(private val api: Api) : VehicleRepository {

    override suspend fun getListVehicle(p1Lat: Double, p1Lon: Double, p2Lat: Double, p2Lon: Double): List<Vehicle> {
        return api.getListVehicle(p1Lat, p1Lon, p2Lat, p2Lon)
            .await()
            .list
    }
}
