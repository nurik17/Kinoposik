package com.example.kinopoisk.ui.movieDetail

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.kinopoisk.R
import com.example.kinopoisk.base.BaseFragment
import com.example.kinopoisk.databinding.FragmentActorDetailBinding
import com.example.kinopoisk.domain.onItemClick
import com.example.kinopoisk.ui.home.adapter.MovieListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ActorDetailFragment : BaseFragment<FragmentActorDetailBinding>(FragmentActorDetailBinding::inflate){

    private val viewModel: ActorDetailsViewModel by viewModels()
    private var adapter = MovieListAdapter{item->
        onItemClick(item,this)
    }

    override fun onBindView() {
        super.onBindView()
        setupUIListeners()
        infoSetUp()
        listOfFilms()

        val id = arguments?.getInt("personId")
        viewModel.getPersonInfo(id!!)
        binding.recyclerActor.adapter = adapter
    }
    private fun listOfFilms(){

        viewModel.filteredList.onEach {
            adapter.submitList(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

    }

    @SuppressLint("SetTextI18n")
    private fun infoSetUp(){
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.actorInfo.collect{
                    binding.apply{
                        actorName.text = it?.nameRu
                        profession.text = it?.profession
                        allFilmographyNumber.text = (it?.films?.size?.toString() + " фильма")
                        Glide.with(actorPhoto)
                            .load(it?.posterUrl)
                            .into(actorPhoto)

                        val actorName = it?.nameRu
                        val personId = it?.personId
                        val films = it?.films
                        binding.allFilmography.setOnClickListener {
                            val bundle = Bundle()
                            val filmographyArrayList = films as? ArrayList<out Parcelable>
                            bundle.putParcelableArrayList("filmography",filmographyArrayList)
                            bundle.putInt("personId",personId!!)
                            bundle.putString("actorName",actorName)
                            findNavController().navigate(R.id.filmographyFragment,bundle)
                        }
                    }
                }
            }
        }
    }
    private fun setupUIListeners() {
        binding.arrowBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}