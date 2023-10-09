package com.example.kinopoisk.ui.movieDetail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.kinopoisk.data.State
import com.example.kinopoisk.databinding.FragmentMovieDetailBinding
import com.example.kinopoisk.domain.onItemClick
import com.example.kinopoisk.domain.onPictureClick
import com.example.kinopoisk.ui.gallery.GalleryViewModel
import com.example.kinopoisk.ui.gallery.GalleryViewModelFactory
import com.example.kinopoisk.ui.gallery.PicturesAdapter
import com.example.kinopoisk.ui.home.adapter.MovieListAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MovieDetailFragment : Fragment() {

    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MovieDetailViewModel
    private lateinit var castAdapter: CastAdapter
    private lateinit var staffAdapter: StaffAdapter
    private lateinit var similarAdapter: MovieListAdapter
    private lateinit var imageAdapter: PicturesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[MovieDetailViewModel::class.java]
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val filmId = arguments?.getInt("filmId")
        val kinopoiskId = arguments?.getInt("kinopoiskId")

        viewModel.getAllDetails(filmId!!)
        viewModel.getAllDetails(kinopoiskId!!)

        setUpRecyclerViews()
        setupUIListeners()
        observeFilmGeneralInfo()
        setUpAdapterInfo()
        observeState()



        imageAdapter = PicturesAdapter { movie, imageView ->
            onPictureClick(movie, imageView, this)
        }
        binding.recyclerGallery.adapter = imageAdapter
        binding.recyclerGallery.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

   /*     if (kinopoiskId != 0) {
            val imageViewModel = ViewModelProvider(
                this, GalleryViewModelFactory(kinopoiskId!!)
            )[GalleryViewModel::class.java]

            imageViewModel.get20Images("STILL")

            imageViewModel.images20.onEach {
                imageAdapter.submitData(PagingData.from(it))
            }.launchIn(viewLifecycleOwner.lifecycleScope)

            kinopoiskId.let { viewModel.getAllDetails(it) }

        } else {
            val imageViewModel = ViewModelProvider(
                this, GalleryViewModelFactory(kinopoiskId)
            )[GalleryViewModel::class.java]
            imageViewModel.get20Images("STILL")

            imageViewModel.images20.onEach {
                imageAdapter.submitData(PagingData.from(it))
            }.launchIn(viewLifecycleOwner.lifecycleScope)

            filmId?.let { viewModel.getAllDetails(it) }
        }
*/


    }


    private fun setUpRecyclerViews() {

        castAdapter = CastAdapter()
        binding.recyclerCast.adapter = castAdapter
        binding.recyclerCast.layoutManager =
            GridLayoutManager(requireContext(), 4, GridLayoutManager.HORIZONTAL, false)

        staffAdapter = StaffAdapter()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


