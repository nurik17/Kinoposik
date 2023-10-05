package com.example.kinopoisk.entity

import android.os.Parcel
import android.os.Parcelable

data class Genre(
    val genre: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString())

    override fun describeContents(): Int {
        return 0    }

    override fun writeToParcel(p0: Parcel, p1: Int) {
        p0.writeString(genre)
    }

    companion object CREATOR : Parcelable.Creator<Genre> {
        override fun createFromParcel(parcel: Parcel): Genre {
            return Genre(parcel)
        }

        override fun newArray(size: Int): Array<Genre?> {
            return arrayOfNulls(size)
        }
    }
}