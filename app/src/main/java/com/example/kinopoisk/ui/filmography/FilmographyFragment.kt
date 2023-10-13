package com.example.kinopoisk.ui.filmography

import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.text.method.TextKeyListener.clear
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.kinopoisk.databinding.FragmentFilmographyBinding
import com.example.kinopoisk.domain.onItemClick
import com.example.kinopoisk.entity.Movie
import com.google.android.material.chip.Chip

class FilmographyFragment : Fragment() {

    private var _binding : FragmentFilmographyBinding? = null
    private val  binding get() = _binding!!

    private val filmographyAdapter = FilmographyAdapter {movie ->
        onItemClick(movie,this)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilmographyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUIListeners()

        val actorName = arguments?.getString("actorName")
        binding.actorName.text = actorName




        val listFilms = when{
            SDK_INT >= 33 -> arguments?.getParcelableArrayList("filmography", Movie::class.java)
            else -> @Suppress("DEPRECATION") arguments?.getParcelableArrayList("filmography")
        }

        val actor = listFilms?.filter {
            it.professionKey == "ACTOR" && !it.description?.contains("озвучка")!!
        }
        val dubbing = listFilms?.filter {
            it.description?.contains("озвучка") == true
        }
        val himself = listFilms?.filter {
            it.description?.contains("самого себя") == true
        }




        if(
            listFilms?.get(0)?.professionKey == "ACTOR" ||
            listFilms?.get(0)?.professionKey == "HERSELF" ||
            listFilms?.get(0)?.professionKey == "HIMSELF"
        ){

            binding.chip1.isChecked = true
            binding.rvFilmography.adapter = filmographyAdapter
            filmographyAdapter.submitList(actor)


            binding.chip1.setOnClickListener {
                binding.rvFilmography.adapter = filmographyAdapter
                filmographyAdapter.submitList(actor)
                clearChipCheck(binding.chip2,binding.chip3)
                binding.chip1.isEnabled = false
                binding.chip2.isEnabled = true
                binding.chip3.isEnabled = true
            }
            binding.chip2.setOnClickListener {
                binding.rvFilmography.adapter = filmographyAdapter
                filmographyAdapter.submitList(dubbing)
                clearChipCheck(binding.chip1,binding.chip3)
                binding.chip2.isEnabled = false
                binding.chip1.isEnabled = true
                binding.chip3.isEnabled = true
            }
            binding.chip3.setOnClickListener {
                binding.rvFilmography.adapter = filmographyAdapter
                filmographyAdapter.submitList(himself)
                clearChipCheck(binding.chip1,binding.chip2)
                binding.chip3.isEnabled = false
                binding.chip1.isEnabled = true
                binding.chip2.isEnabled = true
            }
        }
    }

    private fun clearChipCheck(chip1 : Chip,chip2 : Chip) {
        chip1.isChecked = false
        chip2.isChecked = false
    }

    private fun setupUIListeners() {
        binding.arrowBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}