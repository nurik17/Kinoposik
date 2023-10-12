package com.example.kinopoisk.entity

interface PersonDetails {
    val age: Int
    val birthday: Any
    val birthplace: Any
    val death: Any
    val deathplace: Any
    val facts: List<Any>
    val films: List<Movie>
    val growth: Int
    val hasAwards: Int
    val nameEn: String
    val nameRu: String
    val personId: Int
    val posterUrl: String
    val profession: String
    val sex: String
    val spouses: List<Any>
    val webUrl: String
}