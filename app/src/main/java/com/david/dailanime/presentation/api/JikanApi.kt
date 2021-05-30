package com.david.dailanime.presentation.api

import com.david.dailanime.presentation.list.Anime
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface JikanApi {
    @GET("season")
    fun getAnimeList(): Call<AnimeResponse>


    @GET("anime/{id}")
    fun getAnimeDetails(@Path("id") id: Int): Call<Anime>

}