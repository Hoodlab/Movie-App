package com.example.movieapp.movie.data.remote.api

import com.example.movieapp.BuildConfig
import com.example.movieapp.movie.data.remote.models.MovieDto
import com.example.movieapp.utils.K
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {
    @GET(K.MOVIE_ENDPOINT)
    suspend fun fetchMovie(
        @Query("api_key") apiKey: String = BuildConfig.apiKey,
        @Query("include_adult") includeAdult: Boolean = false
    ): MovieDto
}