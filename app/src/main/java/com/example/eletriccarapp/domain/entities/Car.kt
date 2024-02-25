package com.example.eletriccarapp.domain.entities

import com.google.gson.annotations.SerializedName

class Car(
    @Transient()
    var id: Int,
    @SerializedName("id")
    var idRemote: Int,
    var name: String,
    var price: Float,
    var battery: String,
    var power: String,
    var recharge: String,
    var isFavorite: Boolean,
    var image: String
) {


}