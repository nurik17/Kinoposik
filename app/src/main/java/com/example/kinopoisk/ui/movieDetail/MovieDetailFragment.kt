package com.example.kinopoisk.ui.movieDetail

import android.annotation.SuppressLint
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.kinopoisk.base.BaseFragment
import com.example.kinopoisk.data.State
import com.example.kinopoisk.databinding.FragmentMovieDetailBinding
import com.example.kinopoisk.domain.onActorClick
import com.example.kinopoisk.domain.onItemClick
import com.example.kinopoisk.ui.home.adapter.CastAdapter
import com.example.kinopoisk.ui.home.adapter.MovieListAdapter
import com.example.kinopoisk.ui.home.adapter.StaffAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailFragment : BaseFragment<FragmentMovieDetailBinding>(FragmentMovieDetailBinding::inflate) {

    private val viewModel: MovieDetailViewModel by viewModels()

    private val castAdapter = CastAdapter {item,view ->
        onActorClick(item,view,this)
    }
    private val staffAdapter = StaffAdapter{item,view->
        onActorClick(item,view,this)
    }
    private lateinit var similarAdapter: MovieListAdapter

    override fun onBindView() {
        super.onBindView()
        val filmId = arguments?.getInt("filmId")
        val kinopoiskId = arguments?.getInt("kinopoiskId")

        viewModel.getAllDetails(filmId!!)
        viewModel.getAllDetails(kinopoiskId!!)

        setUpRecyclerViews()
        setupUIListeners()
        observeFilmGeneralInfo()
        setUpAdapterInfo()
        observeState()
    }

    private fun setUpRecyclerViews() {
        binding.recyclerActor.adapter = castAdapter
        binding.recyclerActor.layoutManager =
            GridLayoutManager(requireContext(), 4, GridLayoutManager.HORIZONTAL, false)

        binding.recyclerStaff.adapter = staffAdapter
        binding.recyclerStaff.layoutManager =
            GridLayoutManager(requireContext(), 2, GridLayoutManager.HORIZONTAL, false)

        similarAdapter = MovieListAdapter { movie ->
            onItemClick(movie, this)
        }
        binding.recyclerSimpleFilm.adapter = similarAdapter
        binding.recyclerSimpleFilm.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setUpAdapterInfo() {
        viewModel.staffList.onEach { it ->
            castAdapter.run {
                submitList(it.filter {
                    it.professionKey == "ACTOR"
                })
            }
            binding.castItemNumber.text = it.filter {
                it.professionKey == "ACTOR"
            }.size.toString()

            staffAdapter.run {
                submitList(it.filter {
                    it.professionKey != "ACTOR"
                })
            }
            binding.staffItemNumber.text = it.filter {
                it.professionKey != "ACTOR"
            }.size.toString()
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.similarList.onEach {
            similarAdapter.submitList(it)
            binding.simpleFilmItemNumber.text = it.size.toString()
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    @SuppressLint("SetTextI18n")
    private fun observeFilmGeneralInfo() {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.movieDetail.collect { movieId ->
                    binding.apply {
                        val name =
                            if (movieId?.nameOriginal.isNullOrEmpty()) movieId?.nameRu?.uppercase()
                            else movieId?.nameOriginal?.uppercase()

                        nameEnglish.text = name
                        nameRussian.text = movieId?.nameRu
                        rating.text = movieId?.ratingKinopoisk.toString()
                        year.text = movieId?.year.toString()
                        genre.text = movieId?.genres?.take(2)?.joinToString(", ") {
                            it.genre.toString()
                        }

                        val ratingAgeLimits = movieId?.ratingAgeLimits
                        val ageText = extractAgeText(ratingAgeLimits)
                        age.text = "$ageText+"

                        country.text = movieId?.countries?.get(0)?.country + ","
                        val time = movieId?.filmLength?.toInt()
                        val hours = time?.div(60)
                        val minutes = time?.rem(60)
                        binding.time.text = "$hours ч $minutes мин,"

                        val descriptionText =
                            if (movieId?.shortDescription.isNullOrEmpty()) movieId?.slogan
                            else movieId?.description
                        description.text = descriptionText

                        Glide.with(mainImage)
                            .load(movieId?.posterUrl)
                            .into(mainImage)
                    }
                }
            }
        }
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.state.collect {
                    when (it) {
                        State.Error -> binding.progressCircular.visibility = View.GONE
                        State.Success -> binding.progressCircular.visibility = View.GONE
                        State.Loading -> binding.progressCircular.visibility = View.VISIBLE
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

    private fun extractAgeText(ratingAgeLimits: String?): String {
        val regex = Regex("\\d+")
        val matchResult = regex.find(ratingAgeLimits ?: "")
        return matchResult?.value ?: ""
    }
}


