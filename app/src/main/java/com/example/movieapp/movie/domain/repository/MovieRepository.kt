package com.example.movieapp.movie.domain.repository

import com.example.movieapp.movie.domain.models.Movie
import com.example.movieapp.utils.Response
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun fetchMovie(): Flow<Response<List<Movie>>>
}