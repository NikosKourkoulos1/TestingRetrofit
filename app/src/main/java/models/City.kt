package models

import com.google.gson.annotations.SerializedName

data class City(
    @SerializedName("_id") val id: String,
    val title: String,
    val image: String,
    val numOfEvents: Int
)