package com.david.dailanime.presentation.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.david.dailanime.R

class LikedAnimeList : Fragment() {
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? { // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_anime_resume, container, false)
        Log.d("test","test")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //view.findViewById<View>(R.id.button_second).setOnClickListener {
            //NavHostFragment.findNavController(this@LikedAnimeList)
                    //.navigate(R.id.action_SecondFragment_to_FirstFragment)}

    }
}