package com.example.kinopoisk.domain

import android.os.Bundle
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.kinopoisk.R
import com.example.kinopoisk.entity.Movie
import com.example.kinopoisk.entity.PicturesItem

fun onItemClick(item: Movie, fragment : Fragment){
    val bundle = Bundle()
    val filmId = item.filmId
    val kinopoiskId = item.kinopoiskId

    bundle.putInt("filmId",filmId)
    bundle.putInt("kinopoiskId",kinopoiskId)
    fragment.findNavController().navigate(R.id.movieDetailFragment,bundle)
}
fun onPictureClick(item : PicturesItem,imageView: ImageView,fragment: Fragment){
    val bundle = Bundle()
    val pictureUrl = item.imageUrl
    bundle.putString("pictureUrl",pictureUrl)

}