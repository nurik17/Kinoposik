package com.example.kinopoisk.ui.home.fullMovie

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.kinopoisk.base.BaseFragment
import com.example.kinopoisk.databinding.FragmentFullMovieBinding
import com.example.kinopoisk.domain.onItemClick
import com.example.kinopoisk.ui.home.adapter.MovieListAdapter
import com.example.kinopoisk.ui.home.presentation.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class FullMovieFragment :
    BaseFragment<FragmentFullMovieBinding>(FragmentFullMovieBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()

    private val adapter = MovieListAdapter { movie ->
        onItemClick(movie, this)
    }

    private val pagedAdapter = MovieFullAdapter { movie ->
        onItemClick(movie, this)
    }

    override fun onBindView() {
        super.onBindView()
        getTypeOfMovie()
        /*
                stateType()
        */
        backButton()
        binding.refreshLayout.setOnRefreshListener {
            pagedAdapter.refresh()
        }
        pagedAdapter.loadStateFlow.onEach {
            binding.refreshLayout.isRefreshing = it.refresh == LoadState.Loading
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun backButton() {
        binding.arrowBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getTypeOfMovie() {
        val argsList = arguments?.getStringArrayList("1")

        when {
            argsList?.contains("premieres") == true -> {
                viewModel.getPremieres()
                binding.rvFullMovieList.adapter = adapter
                binding.tvName.text = "Премьеры"
                viewModel.premieres.onEach {
                    adapter.submitList(it)
                }.launchIn(viewLifecycleOwner.lifecycleScope)
            }

            argsList?.contains("top100") == true -> {
                binding.rvFullMovieList.adapter = pagedAdapter
                binding.tvName.text = "Популярное"
                viewModel.getPopular100Paged.onEach {
                    pagedAdapter.submitData(it)
                }.launchIn(viewLifecycleOwner.lifecycleScope)
            }

            argsList?.contains("top250") == true -> {
                binding.rvFullMovieList.adapter = pagedAdapter
                binding.tvName.text = "Топ 250"
                viewModel.top250Paged.onEach {
                    pagedAdapter.submitData(it)
                }.launchIn(viewLifecycleOwner.lifecycleScope)
            }

            argsList?.contains("release") == true -> {
                binding.rvFullMovieList.adapter = pagedAdapter
                binding.tvName.text = "Релизы"
                viewModel.getReleasePaged.onEach {
                    pagedAdapter.submitData(it)
                }.launchIn(viewLifecycleOwner.lifecycleScope)
            }
        }

    }
    /* private fun stateType(){
         viewLifecycleOwner.lifecycleScope.launch {
             lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                 viewModel.state.collect{
                     when(it){
                         State.Error -> {
                             binding.progressCircular.visibility = View.GONE
                             binding.errorText.text = "Нет интернета"
                             binding.imageInternet.visibility = View.VISIBLE
                         }
                         State.Loading -> {
                             binding.progressCircular.visibility = View.VISIBLE
                             binding.errorText.text = ""
                             binding.imageInternet.visibility = View.INVISIBLE
                         }
                         State.Success -> {
                             binding.progressCircular.visibility = View.GONE
                             binding.errorText.text = ""
                             binding.imageInternet.visibility = View.INVISIBLE
                         }
                     }
                 }
             }
         }*/
}