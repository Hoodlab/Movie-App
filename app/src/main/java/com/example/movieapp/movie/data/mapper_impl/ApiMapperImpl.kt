package com.example.movieapp.movie.data.mapper_impl

import com.example.movieapp.common.data.ApiMapper
import com.example.movieapp.movie.data.remote.models.MovieDto
import com.example.movieapp.movie.domain.models.Movie
import com.example.movieapp.utils.GenreConstants

class ApiMapperImpl : ApiMapper<List<Movie>, MovieDto> {
    override fun mapToDomain(apiDto: MovieDto): List<Movie> {
        return apiDto.results.map { result ->
            Movie(
                backdropPath = result.backdropPath,
                genreIds = formatGenre(result.genreIds),
                id = result.id,
                originalLanguage = formatEmptyValue(result.originalLanguage, "language"),
                originalTitle = formatEmptyValue(result.originalTitle, "title"),
                overview = formatEmptyValue(result.overview, "overview"),
                popularity = result.popularity,
                posterPath = result.posterPath,
                releaseDate = formatEmptyValue(result.releaseDate, "date"),
                title = formatEmptyValue(result.title, "title"),
                voteAverage = result.voteAverage,
                voteCount = result.voteCount,
                video = result.video
            )
        }
    }


    private fun formatEmptyValue(value: String?, default: String = ""): String {
        if (value.isNullOrEmpty()) return "Unknown $default"
        return value
    }

    private fun formatGenre(genreIds:List<Int>):List<String>{
        return genreIds.map { GenreConstants.getGenreNameById(it) }
    }

}