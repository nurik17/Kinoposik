package com.example.kinopoisk.ui.search

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kinopoisk.R
import com.example.kinopoisk.base.BaseFragment
import com.example.kinopoisk.databinding.FragmentSearchBinding
import com.example.kinopoisk.ui.search.adapter.SearchAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    private val viewModel: SearchViewModel by activityViewModels()

    private val searchAdapter by lazy {
        SearchAdapter()
    }

    override fun onBindView() {
        super.onBindView()
        setUpRecyclerView()
        searchFunction()

        binding.iconFilter.setOnClickListener {
            findNavController().navigate(R.id.action_searchFragment_to_searchSettingsFragment)
        }
    }

    private fun setUpRecyclerView(){
        val recyclerView = binding.rvSearch
        recyclerView.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        recyclerView.adapter = searchAdapter
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun searchFunction(){
        binding.editText.textInputAsFlow() // extension function
            .onEach { text->
                viewModel.getMovies(text).onEach {
                    searchAdapter.submitData(it)
                }.launchIn(viewLifecycleOwner.lifecycleScope)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }
}