package com.meier.marina.network.repo

import com.meier.marina.network.entity.Vehicle

interface VehicleRepository {

    suspend fun getListVehicle(p1Lat: Double, p1Lon: Double, p2Lat: Double, p2Lon: Double): List<Vehicle>
}
