package com.meier.marina.network.entity

import com.google.gson.annotations.SerializedName

data class Response(

    @SerializedName("poiList")
    val list: List<Vehicle>
)
