package com.example.kinopoisk.entity

import android.os.Parcel
import android.os.Parcelable


data class Movie(
    val countries: List<Country>?,
    val ratingKinopoisk: Double,
    val filmId: Int,
    val kinopoiskId: Int,
    val filmLength: String?,
    val rating: String?,
    val imdbId: String?,
    val genres: List<Genre>?,
    val nameOriginal: String?,
    val nameEn: String?,
    val nameRu: String?,
    val posterUrl: String?,
    val posterUrlPreview: String?,
    val premiereRu: String?,
    val year: Int,
    val type: String?,
    val description: String?,
    val general: Boolean,
    val professionKey: String?,

    val completed: Boolean,
    val coverUrl: String?,
    val editorAnnotation: Any?,
    val endYear: Any?,
    val has3D: Boolean,
    val hasImax: Boolean,
    val isTicketsAvailable: Boolean,
    val kinopoiskHDId: String?,
    val lastSync: String?,
    val logoUrl: Any?,
    val productionStatus: Any?,
    val ratingAgeLimits: String?,
    val ratingAwait: Double,
    val ratingAwaitCount: Int,
    val ratingFilmCritics: Double,
    val ratingFilmCriticsVoteCount: Int,
    val ratingGoodReview: Double,
    val ratingGoodReviewVoteCount: Int,
    val ratingImdb: Double,
    val ratingImdbVoteCount: Int,
    val ratingKinopoiskVoteCount: Int,
    val ratingMpaa: String?,
    val ratingRfCritics: Double,
    val ratingRfCriticsVoteCount: Int,
    val reviewsCount: Int,
    val serial: Boolean,
    val shortDescription: String?,
    val shortFilm: Boolean,
    val slogan: String?,
    val startYear: Any?,
    val webUrl: String?,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.createTypedArrayList(Country.CREATOR),
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createTypedArrayList(Genre.CREATOR),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readString(),
        null,
        null,
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readString(),
        parcel.readString(),
        null,
        null,
        parcel.readString(),
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readString(),
        null,
        parcel.readString()
    )
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(countries)
        parcel.writeDouble(ratingKinopoisk)
        parcel.writeInt(filmId)
        parcel.writeInt(kinopoiskId)
        parcel.writeString(filmLength)
        parcel.writeString(rating)
        parcel.writeString(imdbId)
        parcel.writeTypedList(genres)
        parcel.writeString(nameOriginal)
        parcel.writeString(nameEn)
        parcel.writeString(nameRu)
        parcel.writeString(posterUrl)
        parcel.writeString(posterUrlPreview)
        parcel.writeString(premiereRu)
        parcel.writeInt(year)
        parcel.writeString(type)
        parcel.writeString(description)
        parcel.writeByte(if (general) 1 else 0)
        parcel.writeString(professionKey)
        parcel.writeByte(if (completed) 1 else 0)
        parcel.writeString(coverUrl)
        parcel.writeByte(if (has3D) 1 else 0)
        parcel.writeByte(if (hasImax) 1 else 0)
        parcel.writeByte(if (isTicketsAvailable) 1 else 0)
        parcel.writeString(kinopoiskHDId)
        parcel.writeString(lastSync)
        parcel.writeString(ratingAgeLimits)
        parcel.writeDouble(ratingAwait)
        parcel.writeInt(ratingAwaitCount)
        parcel.writeDouble(ratingFilmCritics)
        parcel.writeInt(ratingFilmCriticsVoteCount)
        parcel.writeDouble(ratingGoodReview)
        parcel.writeInt(ratingGoodReviewVoteCount)
        parcel.writeDouble(ratingImdb)
        parcel.writeInt(ratingImdbVoteCount)
        parcel.writeInt(ratingKinopoiskVoteCount)
        parcel.writeString(ratingMpaa)
        parcel.writeDouble(ratingRfCritics)
        parcel.writeInt(ratingRfCriticsVoteCount)
        parcel.writeInt(reviewsCount)
        parcel.writeByte(if (serial) 1 else 0)
        parcel.writeString(shortDescription)
        parcel.writeByte(if (shortFilm) 1 else 0)
        parcel.writeString(slogan)
        parcel.writeString(webUrl)
    }


    companion object CREATOR : Parcelable.Creator<Movie> {
        override fun createFromParcel(parcel: Parcel): Movie {
            return Movie(parcel)
        }
        override fun newArray(size: Int): Array<Movie?> {
            return arrayOfNulls(size)
        }
    }

    override fun describeContents(): Int {
        return 0
    }
}