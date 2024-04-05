import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testingretrofit.R
import models.City

class CityAdapter(
    private val cities: List<City>,
    private val onCityClicked: (City) -> Unit // Add click listener as a lambda
) : RecyclerView.Adapter<CityAdapter.CityViewHolder>() {

    class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val cityName: TextView = itemView.findViewById(R.id.cityName)
        private val cityImage: ImageView = itemView.findViewById(R.id.cityImage)

        fun bind(city: City, clickListener: (City) -> Unit) {
            cityName.text = city.title
            Glide.with(itemView.context)
                .load(city.image)
                .into(cityImage)

            // Set the click listener for the entire itemView
            itemView.setOnClickListener { clickListener(city) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.city_item, parent, false)
        return CityViewHolder(view)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        // Pass the click listener to the bind function
        holder.bind(cities[position], onCityClicked)
    }

    override fun getItemCount() = cities.size
}
