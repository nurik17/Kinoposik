package com.example.kinopoisk.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kinopoisk.databinding.FragmentSearchBinding
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
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()

        binding.editText.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val keyword = p0.toString()
                viewModel.getMovies(keyword).onEach {
                    searchAdapter.submitData(it)
                }.launchIn(viewLifecycleOwner.lifecycleScope)
            }
        })
    }

    private fun setUpRecyclerView(){
        searchAdapter = SearchAdapter()
        val recyclerView = binding.rvSearch
        recyclerView.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        recyclerView.adapter = searchAdapter
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}