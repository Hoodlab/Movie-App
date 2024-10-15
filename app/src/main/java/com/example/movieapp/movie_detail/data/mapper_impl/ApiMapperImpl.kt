package com.example.movieapp.movie_detail.data.mapper_impl

import com.example.movieapp.common.data.ApiMapper
import com.example.movieapp.movie_detail.data.remote.models.CastDto
import com.example.movieapp.movie_detail.data.remote.models.MovieDetailDto
import com.example.movieapp.movie_detail.domain.models.Cast
import com.example.movieapp.movie_detail.domain.models.MovieDetail
import com.example.movieapp.movie_detail.domain.models.Review
import java.text.SimpleDateFormat
import java.util.Locale

class ApiMovieMapperImpl : ApiMapper<MovieDetail, MovieDetailDto> {
    override fun mapToDomain(apiDto: MovieDetailDto): MovieDetail {
        return MovieDetail(
            backdropPath = apiDto.backdropPath,
            genreIds = apiDto.genres.map { it.name },
            id = apiDto.id,
            originalLanguage = formatEmptyValue(apiDto.originalLanguage, "language"),
            originalTitle = formatEmptyValue(apiDto.originalTitle, "title"),
            overview = formatEmptyValue(apiDto.overview, "overview"),
            popularity = apiDto.popularity,
            posterPath = apiDto.posterPath,
            releaseDate = formatEmptyValue(apiDto.releaseDate, "date"),
            title = formatEmptyValue(apiDto.title, "title"),
            voteAverage = apiDto.voteAverage,
            voteCount = apiDto.voteCount,
            video = apiDto.video,
            cast = formatCast(apiDto.credits.cast),
            language = apiDto.spokenLanguages.map { it.englishName },
            productionCountry = apiDto.productionCountries.map { it.name },
            reviews = apiDto.reviewDto.results.map {
                Review(
                    author = it.author,
                    content = it.content,
                    createdAt = formatTimeStamp(time = it.createdAt),
                    id = it.id,
                    rating = it.authorDetails.rating ?: 0.0
                )
            },
            runTime = convertMinutesToHours(apiDto.runtime)
        )

    }

    private fun formatTimeStamp(pattern: String = "dd.MM.yy", time: String): String {
        // formatter for input date format (assuming ISO 8601)
        val inputDateFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)

        // formatter for desired output format
        val outputDateFormatter = SimpleDateFormat(
            "dd.MM.yy",
            Locale.getDefault()
        ) // Use Locale.getDefault() for user's locale

        // Parse the input date string
        val date = inputDateFormatter.parse(time)

        // Format the parsed date to the desired pattern
        val formattedDate = date?.let { outputDateFormatter.format(it) } ?: time

        return formattedDate
    }

    private fun convertMinutesToHours(minutes: Int): String {
        val hours = minutes / 60
        val remainingMinutes = minutes % 60
        return "${hours}h:${remainingMinutes}m"
    }

    private fun formatEmptyValue(value: String?, default: String = ""): String {
        if (value.isNullOrEmpty()) return "Unknown $default"
        return value
    }

    private fun formatCast(castDto: List<CastDto>): List<Cast> {
        return castDto.map {
            val genderRole = if (it.gender == 2) "Actor" else "Actress"
            Cast(
                id = it.id,
                name = it.name,
                genderRole = genderRole,
                character = it.character,
                profilePath = it.profilePath
            )
        }
    }

}