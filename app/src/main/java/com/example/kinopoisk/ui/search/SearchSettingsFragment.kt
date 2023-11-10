package com.example.kinopoisk.ui.search

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.kinopoisk.R
import com.example.kinopoisk.base.BaseFragment
import com.example.kinopoisk.data.FILM_TYPE
import com.example.kinopoisk.data.SORTING_PARAMS
import com.example.kinopoisk.databinding.FragmentSearchSettingsBinding
import com.google.android.material.slider.RangeSlider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchSettingsFragment : BaseFragment<FragmentSearchSettingsBinding>(FragmentSearchSettingsBinding::inflate) {

    private val viewModel: SearchViewModel by activityViewModels()

   override fun onBindView() {
        super.onBindView()
        navigationBack()
        setUpTextViews() // установка значений textView
        setFilterFilmType()
        setRangeSlider()
        setFilterSorting()
        onClickYearPicker()
    }

    private fun setUpTextViews() {
        binding.apply {
            viewLifecycleOwner.lifecycleScope.launch {
                lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.filterFlow.collect { filter ->
                        val country = filter.countries
                        val genres = filter.genres
                        buttonGroup.check(
                            when (filter.type) {
                                FILM_TYPE[1] -> filmFilterBtn.id
                                FILM_TYPE[2] -> serialsFilterBtn.id
                                else -> allFilterBtn.id
                            }
                        )
                        tvCountryTitle.text =
                            if (country.values.isNotEmpty()) country.values.first()
                            else getString(R.string.search_filters_countries_default)
                        buttonGroup2.check(
                            when (filter.order) {
                                SORTING_PARAMS[0] -> yearFilterBtn.id
                                SORTING_PARAMS[1] -> popularFilterBtn.id
                                else -> ratingFilterBtn.id
                            }
                        )
                        tvGenreTitle.text = if (genres.values.isNotEmpty()) genres.values.first()
                        else getString(R.string.search_filters_genres_default)

                        tvYearTitle.text = getString(
                            R.string.search_settings_years_text,
                            filter.yearFrom, filter.yearTo
                        )
                    }
                }
            }
        }
    }

    private fun setFilterFilmType() {
        binding.buttonGroup.addOnButtonCheckedListener { group, checkedId, isChecked ->
            when (checkedId) {
                binding.allFilterBtn.id -> {
                    viewModel.updateFiltersFull(
                        viewModel.getFiltersFull().copy(type = FILM_TYPE[0])
                    )
                }

                binding.filmFilterBtn.id -> {
                    viewModel.updateFiltersFull(
                        viewModel.getFiltersFull().copy(type = FILM_TYPE[1])
                    )
                }

                else -> {
                    viewModel.updateFiltersFull(
                        viewModel.getFiltersFull().copy(type = FILM_TYPE[2])
                    )
                }
            }
        }
    }

    private fun setRangeSlider() {
        binding.slider.addOnSliderTouchListener(object :
            RangeSlider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: RangeSlider) {
                if (slider.values == listOf(1, 10)) {
                    binding.tvRatingTitle.text = "любой"
                } else {
                    val values = slider.values.map { it.toInt() }
                    binding.tvRatingTitle.text =
                        resources.getString(
                            R.string.search_settings_rating_text,
                            values[0],
                            values[1],
                        )
                    binding.tvRating1.text = values[0].toString()
                    binding.tvRating10.text = values[1].toString()
                    viewModel.updateFiltersFull(
                        viewModel.getFiltersFull().copy(
                            ratingFrom = values[0], ratingTo = values[1]
                        )
                    )
                }
            }

            override fun onStopTrackingTouch(slider: RangeSlider) {
                if (slider.values == listOf(1, 10)) {
                    binding.tvRatingTitle.text = "любой"
                } else {
                    val values = slider.values.map { it.toInt() }
                    binding.tvRatingTitle.text =
                        resources.getString(
                            R.string.search_settings_rating_text,
                            values[0],
                            values[1],
                        )
                    binding.tvRating1.text = values[0].toString()
                    binding.tvRating10.text = values[1].toString()
                    viewModel.updateFiltersFull(
                        viewModel.getFiltersFull().copy(
                            ratingFrom = values[0], ratingTo = values[1]
                        )
                    )
                }
            }
        })
    }

    private fun setFilterSorting(){
        binding.buttonGroup2.addOnButtonCheckedListener { group, checkedId, isChecked ->
            when (checkedId) {
                binding.yearFilterBtn.id -> {
                    viewModel.updateFiltersFull(
                        viewModel.getFiltersFull().copy(type = SORTING_PARAMS[0])
                    )
                }

                binding.popularFilterBtn.id -> {
                    viewModel.updateFiltersFull(
                        viewModel.getFiltersFull().copy(type = SORTING_PARAMS[1])
                    )
                }

                else -> {
                    viewModel.updateFiltersFull(
                        viewModel.getFiltersFull().copy(type = SORTING_PARAMS[2])
                    )
                }
            }
        }
    }
    private fun onClickYearPicker(){
        binding.tvYearTitle.setOnClickListener {
            findNavController().navigate(R.id.action_searchSettingsFragment_to_yearPickerFragment)
        }
    }

    /*private fun onClickFilterCountryGenreChoose(){
        binding.tvCountryTitle.setOnClickListener {
            val action =
        }
    }*/

    private fun navigationBack() {
        binding.arrowBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}