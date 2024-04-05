import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testingretrofit.R
import models.Event
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class EventsAdapter(private val events: List<Event>) : RecyclerView.Adapter<EventsAdapter.EventViewHolder>() {

    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val eventName: TextView = itemView.findViewById(R.id.eventName)
        private val eventDate: TextView = itemView.findViewById(R.id.eventDate)
        private val eventImage: ImageView = itemView.findViewById(R.id.eventImage)

        fun bind(event: Event) {
            eventName.text = event.title
            eventDate.text = formatDateString(event.date)
            Glide.with(itemView.context).load(event.image).into(eventImage)
        }

        private fun formatDateString(dateString: String): String {
            return try {
                // Updated original format to include milliseconds and the 'Z' as UTC timezone
                val originalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).apply {
                    timeZone = TimeZone.getTimeZone("UTC") // Ensuring UTC is used for parsing
                }

                // Target format for displaying the date
                val targetFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

                val date = originalFormat.parse(dateString)
                targetFormat.format(date ?: return "Invalid date") // Format and return the date, or "Invalid date" if parsing fails
            } catch (e: ParseException) {
                e.printStackTrace() // Print the stack trace for debugging
                "Invalid date"
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.event_item, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(events[position])
    }

    override fun getItemCount() = events.size
}
