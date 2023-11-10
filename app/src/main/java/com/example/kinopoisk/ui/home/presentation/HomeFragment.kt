package com.example.kinopoisk.ui.home.presentation

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.kinopoisk.R
import com.example.kinopoisk.base.BaseFragment
import com.example.kinopoisk.data.State
import com.example.kinopoisk.databinding.FragmentHomeBinding
import com.example.kinopoisk.ui.home.adapter.VerticalAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val verticalAdapter = VerticalAdapter(this)

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onBindView() {
        super.onBindView()
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
                    when(it) {
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
}