package com.example.kinopoisk.ui.search

import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kinopoisk.R
import com.example.kinopoisk.base.BaseFragment
import com.example.kinopoisk.databinding.FragmentSearchFiltersBinding
import com.example.kinopoisk.entity.FilterCountry
import com.example.kinopoisk.entity.FilterCountryGenre
import com.example.kinopoisk.ui.search.adapter.SearchFilterAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentSearchFilters :
    BaseFragment<FragmentSearchFiltersBinding>(FragmentSearchFiltersBinding::inflate) {

    private val viewModel: SearchViewModel by activityViewModels()
    private lateinit var adapter: SearchFilterAdapter


    override fun onBindView() {
        super.onBindView()
        val args: FragmentSearchFiltersArgs by navArgs()
        setAdapter()
        getFilterList(args.filterType)
        setSearchVewListener(args.filterType)
        binding.arrowBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setAdapter() {
        val divider = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        val dividerResource =
            ResourcesCompat.getDrawable(resources, R.drawable.recycler_divider, null)
        divider.setDrawable(dividerResource!!)

        adapter = SearchFilterAdapter { onFilterItemClick(it)}
        binding.searchFiltersRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.searchFiltersRv.addItemDecoration(divider)
    }

    private fun getFilterList(filterType: String){
        viewModel.setFilterValues(filterType)
        binding.searchFiltersSv.hint = when(filterType){
            KEY_COUNTRY -> "Введите страну"
            KEY_GENRE -> "Введите жанр"
            else-> ""
        }
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.filterValuesCountriesGenres.collect{
                    adapter.submitList(it)
                }
            }
        }
    }
    private fun setSearchVewListener(filterType: String) {
        binding.searchFiltersSv.doOnTextChanged { text, _, _, _ ->
            viewModel.updateFilterCountriesGenres(filterType, text.toString())
        }
    }
    private fun onFilterItemClick(filterType: FilterCountryGenre){
        val newFilterValues = when(filterType){
            is FilterCountry ->{
                viewModel.getFiltersFull().copy(countries = mapOf(filterType.id to filterType.name))
            }
            else->{
                viewModel.getFiltersFull().copy(genres = mapOf(filterType.id to filterType.name))
            }
        }
        viewModel.updateFiltersFull(newFilterValues)
    }

    companion object {
        const val KEY_COUNTRY = "country"
        const val KEY_GENRE = "genre"
    }
}