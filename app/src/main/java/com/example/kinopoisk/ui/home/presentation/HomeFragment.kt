package com.example.kinopoisk.ui.home.presentation

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
import com.example.kinopoisk.R
import com.example.kinopoisk.data.MovieListRepository
import com.example.kinopoisk.data.State
import com.example.kinopoisk.databinding.FragmentHomeBinding
import com.example.kinopoisk.domain.RetrofitClient
import com.example.kinopoisk.ui.home.adapter.VerticalAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val verticalAdapter = VerticalAdapter(this)
    private lateinit var homeViewModel: HomeViewModel

    private val repository: MovieListRepository by lazy {
        MovieListRepository(RetrofitClient.api)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = HomeViewModel.HomeViewModelFactory(
            repository = repository
        )

        homeViewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerNewMovies.adapter = verticalAdapter

        setUpMovies()
        stateMovies()
    }

    private fun setUpMovies() {
        homeViewModel.genresList.onEach {
            verticalAdapter.setMovies(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun stateMovies() {
        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.btm_nav)

        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                homeViewModel.state.collect {
                    when (it) {
                        State.Error -> {
                            binding.errorText.text = "No internet connection"
                            binding.loaderImage.visibility = View.GONE
                            binding.progressCircular.visibility = View.GONE
                            bottomNavigationView.visibility = View.GONE
                            binding.imageInternet.visibility = View.VISIBLE
                        }

                        State.Success -> {
                            binding.errorText.text = ""
                            binding.loaderImage.visibility = View.INVISIBLE
                            binding.progressCircular.visibility = View.GONE
                            binding.recyclerNewMovies.visibility = View.VISIBLE
                            bottomNavigationView.visibility = View.VISIBLE
                            binding.imageInternet.visibility = View.INVISIBLE
                        }

                        State.Loading -> {
                            binding.loaderImage.visibility = View.VISIBLE
                            binding.progressCircular.progress = View.VISIBLE
                            binding.recyclerNewMovies.visibility = View.GONE
                            bottomNavigationView.visibility = View.GONE
                            binding.imageInternet.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}