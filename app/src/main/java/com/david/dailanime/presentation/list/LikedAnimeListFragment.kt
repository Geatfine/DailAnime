package com.david.dailanime.presentation.list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.david.dailanime.R
import com.david.dailanime.presentation.api.AnimeResponse
import com.david.dailanime.presentation.singleton.Singletons
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class LikedAnimeListFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private val adapter = DailanimeAdapter(mutableListOf() , ::onClickedAnime)
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? { // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_liked_anime_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.anime_liked_recycler_view)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@LikedAnimeListFragment.adapter
        }
        adapter.defineSharedPreferences(context as Context)
        showList()
    }


    fun showError() {
        Toast.makeText(context,"API Error",Toast.LENGTH_SHORT).show()
    }
    private fun onClickedAnime(anime: Anime) {
        findNavController(this).navigate(R.id.action_listAnime_to_AnimeResume, bundleOf("mal_id" to anime.mal_id))

    }

    private fun showList(){
        adapter.setList(adapter.getDataFromCache())
    }

}

    /*    view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            NavHostFragment.findNavController(AnimeListFragment.this)
                    .navigate(R.id.action_FirstFragment_to_SecondFragment);
        }
    });
    */
