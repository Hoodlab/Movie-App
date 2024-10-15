package com.example.movieapp.movie_detail.data.remote.api

import com.example.movieapp.BuildConfig
import com.example.movieapp.movie_detail.data.remote.models.MovieDetailDto
import com.example.movieapp.utils.K
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val MOVIE_ID = "movie_id"

interface MovieDetailApiService {

    @GET("${K.MOVIE_DETAIL_ENDPOINT}/{$MOVIE_ID}")
    suspend fun fetchMovie(
        @Path(MOVIE_ID) movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.apiKey,
        @Query("append_to_response") appendToResponse: String = "credits,reviews"
    ): MovieDetailDto
}