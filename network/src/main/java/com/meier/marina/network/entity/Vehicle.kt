package com.meier.marina.network.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Vehicle(

    val id: Long,

    val coordinate: Coordinate,

    val fleetType: FleetType,

    val heading: Double
) : Parcelable

@Parcelize
data class Coordinate(

    val latitude: Double,

    val longitude: Double
) : Parcelable

enum class FleetType {
    POOLING, TAXI
}
