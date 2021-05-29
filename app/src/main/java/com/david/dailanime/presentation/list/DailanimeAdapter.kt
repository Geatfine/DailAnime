package com.david.dailanime.presentation.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.david.dailanime.R
import com.squareup.picasso.Picasso
import java.util.*

class DailanimeAdapter(private var localDataSet: List<Anime>, private var onclick :((Anime) -> Unit)? = null) : RecyclerView.Adapter<DailanimeAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView
        val imageView : ImageView

        init {
            // Define click listener for the ViewHolder's View
            textView = view.findViewById<View>(R.id.anime_name) as TextView

            imageView = view.findViewById<View>(R.id.anime_image) as ImageView

        }
    }

    fun setList(list: List<Anime>) {
        localDataSet = list
        notifyDataSetChanged()
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet List containing the data to populate views to be used
     * by RecyclerView.
     */
// Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder { // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.dailanimeitem, viewGroup, false)
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) { // Get element from your dataset at this position and replace the
// contents of the view with that element
        viewHolder.textView.text = localDataSet[position].title

        Picasso.get().load(localDataSet[position].image_url).into(viewHolder.imageView)

        viewHolder.itemView.setOnClickListener {
            onclick?.invoke(localDataSet[position])
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return localDataSet.size
    }
}