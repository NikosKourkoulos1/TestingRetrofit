package com.example.testingretrofit
import CityAdapter
import EventsAdapter
import network.RetrofitClient
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import models.City
import models.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventsActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var eventsAdapter: EventsAdapter
    private var eventsList = mutableListOf<Event>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setLogo(R.drawable.logo)
        supportActionBar?.setDisplayUseLogoEnabled(true)

        recyclerView = findViewById(R.id.eventsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        eventsAdapter = EventsAdapter(eventsList)
        recyclerView.adapter = eventsAdapter

        val cityId = intent.getStringExtra("CITY_ID")
        if (cityId != null) {
            fetchEventsForCity(cityId)
        } else {
            Log.e("EventsActivity", "City ID is null")
            Toast.makeText(this, "Error: City not specified", Toast.LENGTH_LONG).show()
        }
    }

    private fun fetchEventsForCity(cityId: String) {
        // Assuming RetrofitClient is your configured Retrofit instance
        RetrofitClient.apiService.getEventsForCity(cityId).enqueue(object : Callback<List<Event>> {
            override fun onResponse(call: Call<List<Event>>, response: Response<List<Event>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        eventsList.clear()
                        eventsList.addAll(it)
                        eventsAdapter.notifyDataSetChanged()
                    }
                } else {
                    Toast.makeText(this@EventsActivity, "Error fetching events", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Event>>, t: Throwable) {
                Toast.makeText(this@EventsActivity, "Network error", Toast.LENGTH_SHORT).show()
            }
        })
    }
}


