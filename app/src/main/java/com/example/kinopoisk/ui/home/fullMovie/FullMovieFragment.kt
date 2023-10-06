package com.example.kinopoisk.ui.home.fullMovie

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
import androidx.navigation.fragment.findNavController
import com.example.kinopoisk.data.MovieListRepository
import com.example.kinopoisk.data.State
import com.example.kinopoisk.databinding.FragmentFullMovieBinding
import com.example.kinopoisk.domain.RetrofitClient
import com.example.kinopoisk.ui.home.adapter.MovieListAdapter
import com.example.kinopoisk.ui.home.presentation.HomeViewModel
import com.example.kinopoisk.utils.IOnBackPressed
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class FullMovieFragment : Fragment(){

    private var _binding: FragmentFullMovieBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel : HomeViewModel

    private val adapter = MovieListAdapter{}

    private val pagedAdapter = MovieFullAdapter{movie ->
    }
    private val repository: MovieListRepository by lazy {
        MovieListRepository(RetrofitClient.api)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFullMovieBinding.inflate(inflater,container,false)

        val factory = HomeViewModel.HomeViewModelFactory(
            repository = repository
        )
        viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]

        return _binding!!.root
    }


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getTypeOfMovie()
        stateType()

        binding.arrowBack.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    private fun getTypeOfMovie(){
        val argsList = arguments?.getStringArrayList("1")

        when{
            argsList?.contains("premieres") == true -> {
                viewModel.getPremieres()
                binding.rvFullMovieList.adapter = adapter
                binding.tvName.text = "Премьеры"
                viewModel.premieres.onEach {
                    adapter.submitList(it)
                }.launchIn(viewLifecycleOwner.lifecycleScope)
            }

            argsList?.contains("top100") == true ->{
                binding.rvFullMovieList.adapter = pagedAdapter
                binding.tvName.text = "Популярное"
                viewModel.getPopular100Paged.onEach {
                    pagedAdapter.submitData(it)
                }.launchIn(viewLifecycleOwner.lifecycleScope)
            }
            argsList?.contains("top250") == true ->{
                binding.rvFullMovieList.adapter = pagedAdapter
                binding.tvName.text = "Топ 250"
                viewModel.top250Paged.onEach {
                    pagedAdapter.submitData(it)
                }.launchIn(viewLifecycleOwner.lifecycleScope)
            }
            argsList?.contains("release") == true ->{
                binding.rvFullMovieList.adapter = pagedAdapter
                binding.tvName.text = "Релизы"
                viewModel.getReleasePaged.onEach {
                    pagedAdapter.submitData(it)
                }.launchIn(viewLifecycleOwner.lifecycleScope)
            }
        }

    }
    private fun stateType(){
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
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}