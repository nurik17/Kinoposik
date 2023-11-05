package com.example.kinopoisk.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.kinopoisk.R
import com.example.kinopoisk.data.FILM_TYPE
import com.example.kinopoisk.data.SORTING_PARAMS
import com.example.kinopoisk.databinding.FragmentSearchSettingsBinding
import com.google.android.material.slider.RangeSlider
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SearchSettingsFragment : Fragment() {
    private var _binding: FragmentSearchSettingsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigationBack()
        binding.tvScreenName.text = "Настройки поиска"
        setTextViews()
        setFilterFilmType()
        setFilterRangeSlider()
        setFilterSorting()
    }

    private fun setTextViews() {
        binding.apply {
            viewLifecycleOwner.lifecycleScope.launch {
                lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.filterFlow.collect { filter ->
                        val country = filter.countries
                        val genre = filter.genres
                        val materialToggleGroup = buttonGroup
                        when (filter.type) {
                            FILM_TYPE[1] -> materialToggleGroup.check(R.id.film_filter_btn)
                            FILM_TYPE[2] -> materialToggleGroup.check(R.id.serials_filter_btn)
                            else -> allFilterBtn.id
                        }
                        tvCountryTitle.text =
                            if (country.values.isNotEmpty()) country.values.first()
                            else getString(R.string.search_filters_countries_default)
                        tvGenreTitle.text =
                            if (genre.values.isNotEmpty()) genre.values.first()
                            else getString(R.string.search_filters_countries_default)
                        tvYearTitle.text =
                            getString(
                                R.string.search_settings_years_text,
                                filter.yearFrom, filter.yearTo
                            )
                        when (filter.order) {
                            SORTING_PARAMS[0] -> materialToggleGroup.check(R.id.year_filter_btn)
                            SORTING_PARAMS[1] -> materialToggleGroup.check(R.id.popular_filter_btn)
                            else -> ratingFilterBtn.id
                        }
                    }
                }
            }
        }
    }

    private fun setFilterFilmType() {
        val materialToggleGroup = binding.buttonGroup
        materialToggleGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    binding.filmFilterBtn.id -> {
                        viewModel.updateFiltersFull(
                            viewModel.getFiltersFull().copy(type = FILM_TYPE[1])
                        )
                    }

                    binding.allFilterBtn.id -> {
                        viewModel.updateFiltersFull(
                            viewModel.getFiltersFull().copy(type = FILM_TYPE[0])
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
    }

    private fun setFilterSorting() {
        val materialToggleGroup = binding.buttonGroup2
        materialToggleGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
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
    }

    private fun setFilterRangeSlider() {
        binding.apply {
            tvRating1.text =
                resources.getInteger(R.integer.settings_rating_slider_start).toString()
            tvRating10.text =
                resources.getInteger(R.integer.settings_rating_slider_end).toString()
            slider.addOnSliderTouchListener(object :
                RangeSlider.OnSliderTouchListener {
                override fun onStartTrackingTouch(slider: RangeSlider) {
                    if (slider.values == listOf(1f, 10f)) {
                        tvRatingTitle.text = "любой"
                    } else {
                        val values = slider.values.map { it.toInt() }
                        tvRatingTitle.text =
                            resources.getString(
                                R.string.search_settings_rating_text,
                                values[0],
                                values[1]
                            )
                        tvRating1.text = values[0].toString()
                        tvRating10.text = values[1].toString()
                        viewModel.updateFiltersFull(
                            viewModel.getFiltersFull().copy(
                                ratingFrom = values[0], ratingTo = values[1]
                            )
                        )
                    }
                }

                override fun onStopTrackingTouch(slider: RangeSlider) {
                    if (slider.values == listOf(1f, 10f)) {
                        tvRatingTitle.text = "любой"
                    } else {
                        val values = slider.values.map { it.toInt() }
                        tvRatingTitle.text =
                            resources.getString(
                                R.string.search_settings_rating_text,
                                values[0],
                                values[1]
                            )
                        tvRating1.text = values[0].toString()
                        tvRating10.text = values[1].toString()
                        viewModel.updateFiltersFull(
                            viewModel.getFiltersFull().copy(
                                ratingFrom = values[0], ratingTo = values[1]
                            )
                        )
                    }
                }
            })
        }
    }

    private fun navigationBack() {
        binding.arrowBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}