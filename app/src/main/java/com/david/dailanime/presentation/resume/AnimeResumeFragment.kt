package com.david.dailanime.presentation.resume

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.david.dailanime.R
import com.david.dailanime.presentation.api.AnimeResponse
import com.david.dailanime.presentation.api.AnimeResumeResponse
import com.david.dailanime.presentation.list.Anime
import com.david.dailanime.presentation.singleton.Singletons
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AnimeResumeFragment : Fragment() {

    private lateinit var title_name: TextView
    private lateinit var imageView : ImageView
    private lateinit var score : TextView
    private lateinit var nombre_episode : TextView
    private lateinit var anime_resume : TextView
    private lateinit var watch_list_add : Button



    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? { // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_anime_resume, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        title_name = view.findViewById<View>(R.id.title_anime) as TextView
        imageView = view.findViewById<View>(R.id.anime_image) as ImageView
        score = view.findViewById<View>(R.id.score) as TextView
        nombre_episode = view.findViewById<View>(R.id.nombre_episode) as TextView
        anime_resume = view.findViewById<View>(R.id.anime_resume) as TextView


        animeApiCall()
    }
    fun showError() {
        Toast.makeText(context,"API Error", Toast.LENGTH_SHORT).show()
    }


    private fun animeApiCall(){
        Singletons.jikanApi.getAnimeDetails(arguments?.getInt("mal_id")?:-1).enqueue(object : Callback<Anime> {



            override fun onFailure(call: Call<Anime>, t: Throwable) {
                showError()
            }

            override fun onResponse(call: Call<Anime>, response: Response<Anime>) {
                if (response.isSuccessful && response.body() != null) {
                    val animeResponse: Anime = response.body()!!
                    title_name.text = animeResponse.title
                    Picasso.get().load(animeResponse.image_url).into(imageView)
                    score.text = "score" + animeResponse.score.toString()
                    anime_resume.text = animeResponse.synopsis
                    nombre_episode.text = "episode : " + animeResponse.episodes

                }
                else
                showError()
            }

        })

    }
}