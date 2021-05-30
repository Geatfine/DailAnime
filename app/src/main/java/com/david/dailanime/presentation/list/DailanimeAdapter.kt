package com.david.dailanime.presentation.list

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.david.dailanime.R
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.squareup.picasso.Picasso
import java.lang.reflect.Type


class DailanimeAdapter(private var localDataSet: MutableList<Anime>, private var onclick :((Anime) -> Unit)? = null) : RecyclerView.Adapter<DailanimeAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */

    lateinit var sharedPreferences: SharedPreferences
    val  gson : Gson = GsonBuilder().setLenient().create()



    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView
        val imageView : ImageView
        val switch : Switch

        init {
            // Define click listener for the ViewHolder's View
            textView = view.findViewById<View>(R.id.anime_name) as TextView
            switch = view.findViewById<View>(R.id.switch1) as Switch
            imageView = view.findViewById<View>(R.id.anime_image) as ImageView

        }
    }

    fun defineSharedPreferences(context: Context){
        sharedPreferences = context.getSharedPreferences("dailanime",Context.MODE_PRIVATE)
    }

    fun setList(list: MutableList<Anime>) {
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
        if(setSwitchChecked(localDataSet[position])){
            viewHolder.switch.setChecked(true)
        }else{
            viewHolder.switch.setChecked(false)

        }

        viewHolder.switch.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked) {
                saveListAnime(localDataSet[position])
                viewHolder.switch.setChecked(true)
            } else {
                deletefromList(localDataSet[position])
                viewHolder.switch.setChecked(false)
            }
        }

    }

     fun saveListAnime(anime : Anime){
        val jsonAnime : String?
        val animeList : MutableList<Anime>
         val type : Type

         jsonAnime = sharedPreferences.getString("jsondailanime" , null)
        if(jsonAnime != null){
            type = object:TypeToken<List<Anime>>(){}.type
            animeList = gson.fromJson(jsonAnime,type)
            animeList.add(anime)
            sharedPreferences.edit().remove("jsondailanime")
            sharedPreferences.edit().putString("jsondailanime",gson.toJson(animeList)).apply()
        }else {//1st anime to add
            animeList = mutableListOf(anime)
            sharedPreferences.edit().putString("jsondailanime", gson.toJson(animeList)).apply()
        }
     }

     fun deletefromList(anime : Anime){
        val jsonAnime : String?
        val animeList : MutableList<Anime>
         var i = 0
        jsonAnime = sharedPreferences.getString("jsondailanime" , null)
        if(jsonAnime != null){
            val type : Type = object:TypeToken<List<Anime>>(){}.type
            animeList = gson.fromJson(jsonAnime,type)
            while (i < animeList.size ){
                if (anime.mal_id == animeList.get(i).mal_id) {
                    animeList.removeAt(i)
                    localDataSet.remove(anime)
                    notifyDataSetChanged()
                    i--
                }
                i++
            }
            sharedPreferences.edit().remove("jsondailanime")
            sharedPreferences.edit().putString("jsondailanime",gson.toJson(animeList)).apply()
        }
     }

    fun getDataFromCache() : MutableList<Anime>{
        val jsonAnime : String?
        val animeList : List<Anime>?
        jsonAnime = sharedPreferences.getString("jsondailanime", null)
        if(jsonAnime != null){
            val type : Type = object :TypeToken<List<Anime>>(){}.type
            return gson.fromJson(jsonAnime,type)
        }else
            return mutableListOf()
    }
    fun setSwitchChecked(data: Anime) : Boolean{
        val animelist: List<Anime> = getDataFromCache()
        for (anime in animelist){
            if (anime.mal_id == data.mal_id){
                return true
            }
        }
        return false
    }



    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return localDataSet.size
    }
}