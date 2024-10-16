package com.example.movieapp.movie_detail.data.repository_impl

import com.example.movieapp.common.data.ApiMapper
import com.example.movieapp.movie.data.remote.api.MovieApiService
import com.example.movieapp.movie.data.remote.models.MovieDto
import com.example.movieapp.movie.domain.models.Movie
import com.example.movieapp.movie_detail.data.remote.api.MovieDetailApiService
import com.example.movieapp.movie_detail.data.remote.models.MovieDetailDto
import com.example.movieapp.movie_detail.domain.models.MovieDetail
import com.example.movieapp.movie_detail.domain.repository.MovieDetailRepository
import com.example.movieapp.utils.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class MovieDetailRepositoryImpl(
    private val movieDetailApiService: MovieDetailApiService,
    private val movieApiService: MovieApiService,
    private val apiDetailMapper: ApiMapper<MovieDetail, MovieDetailDto>,
    private val apiMovieMapper: ApiMapper<List<Movie>, MovieDto>
) : MovieDetailRepository {
    override fun fetchMovieDetail(movieId: Int): Flow<Response<MovieDetail>> = flow {
        emit(Response.Loading())
        val movieDto = movieDetailApiService.fetchMovie(movieId = movieId)
        apiDetailMapper.mapToDomain(movieDto).apply {
            emit(Response.Success(this))
        }
    }.catch { e ->
        e.printStackTrace()
        emit(Response.Error(e))
    }

    override fun fetchMovie(): Flow<Response<List<Movie>>> = flow {
        emit(Response.Loading())
        val movieDto = movieApiService.fetchMovie()
        apiMovieMapper.mapToDomain(movieDto).apply {
            emit(Response.Success(this))
        }
    }.catch { e ->
        emit(Response.Error(e))
    }
}