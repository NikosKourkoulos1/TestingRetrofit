package network

import models.City
import models.Event
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("cities")
    fun getCities(): Call<List<City>>
    @GET("cities/{cityId}/events")
    fun getEventsForCity(@Path("cityId") cityId: String): Call<List<Event>>
}
