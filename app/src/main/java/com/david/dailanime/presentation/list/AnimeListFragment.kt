package com.david.dailanime.presentation.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.david.dailanime.R
import com.david.dailanime.presentation.api.AnimeResponse
import com.david.dailanime.presentation.api.JikanApi
import com.david.dailanime.presentation.singleton.Singletons
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import retrofit2.Callback

class AnimeListFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private val adapter = DailanimeAdapter(listOf() , ::onClickedAnime)
    private val layoutManager = LinearLayoutManager(context)
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? { // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_anime_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.anime_recycler_view)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@AnimeListFragment.adapter
        }
        animeApiCall()

    }


    fun showError() {
        Toast.makeText(context,"API Error",Toast.LENGTH_SHORT).show()
    }
    private fun onClickedAnime(anime: Anime) {
       NavHostFragment.findNavController(this).navigate(R.id.action_listAnime_to_AnimeResume, bundleOf("anime_id" to anime.mal_id))

    }


    fun animeApiCall(){
        Singletons.jikanApi.getAnimeList().enqueue(object : Callback<AnimeResponse> {
            override fun onFailure(call: Call<AnimeResponse>, t: Throwable) {
                showError()
            }

            override fun onResponse(
                    call: Call<AnimeResponse>,
                    response: Response<AnimeResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val animeResponse: AnimeResponse = response.body()!!
                    adapter.setList(animeResponse.anime)
                }
                else
                    showError()
            }

        })
    }

        /*    view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(AnimeListFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
        */
}
