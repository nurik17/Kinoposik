package com.example.kinopoisk.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kinopoisk.R
import com.example.kinopoisk.databinding.FragmentSearchBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class SearchFragment : Fragment() {

    private var _binding : FragmentSearchBinding? = null
    private val  binding get() = _binding!!

    private lateinit var viewModel : SearchViewModel

    private lateinit var searchAdapter: SearchAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        searchFunction()

        binding.iconFilter.setOnClickListener {
            findNavController().navigate(R.id.action_searchFragment_to_searchSettingsFragment)
        }
    }

    private fun setUpRecyclerView(){
        searchAdapter = SearchAdapter()
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
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}