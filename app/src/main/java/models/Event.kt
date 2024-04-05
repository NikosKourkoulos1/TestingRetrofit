package models

import com.google.gson.annotations.SerializedName

data class Event(
    @SerializedName("_id") val id: String,
    val title: String,
    val category: String,
    val description: String,
    val date: String, // Dates will be represented as Strings here; conversion might be necessary
    val time: String,
    val remainingTickets: Int,
    val ticketPrice: Double,
    val image: String?
)
