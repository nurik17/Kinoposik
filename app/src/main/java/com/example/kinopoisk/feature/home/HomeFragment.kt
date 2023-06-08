package com.example.kinopoisk.feature.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kinopoisk.databinding.FragmentHomeBinding
import com.example.kinopoisk.feature.home.adapter.ParentAdapter
import com.example.kinopoisk.feature.home.repository.MovieListRepository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!

    private lateinit var homeViewModel : HomeViewModel
    private lateinit var adapter : ParentAdapter

    private val repository: MovieListRepository by lazy {
        MovieListRepository()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = HomeViewModel.Factory(
            repository = repository
        )
        homeViewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        setUpViews()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.movies.onEach {
        }.launchIn(lifecycleScope)
    }
    private fun setUpViews(){

        binding.rvMain.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        adapter = ParentAdapter()
        binding.rvMain.adapter = adapter
    }
}