package com.example.testingretrofit

import CityAdapter
import android.content.Intent
import network.RetrofitClient
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import models.City
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setLogo(R.drawable.logo)
        supportActionBar?.setDisplayUseLogoEnabled(true)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        fetchCities()
    }

    private fun fetchCities() {
        RetrofitClient.apiService.getCities().enqueue(object : Callback<List<City>> {
            override fun onResponse(call: Call<List<City>>, response: Response<List<City>>) {
                if (response.isSuccessful) {
                    response.body()?.let { cities ->
                        // Instantiate CityAdapter with a click listener
                        recyclerView.adapter = CityAdapter(cities) { city ->
                            // Here, start EventsActivity and pass the selected city's ID
                            val intent = Intent(this@MainActivity, EventsActivity::class.java).apply {
                                putExtra("CITY_ID", city.id)
                            }
                            startActivity(intent)
                        }
                    }
                } else {
                    showError("Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<City>>, t: Throwable) {
                showError("Failure: ${t.message}")
            }
        })
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
