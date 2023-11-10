package com.example.kinopoisk.ui.filmography

import android.os.Build
import androidx.navigation.fragment.findNavController
import com.example.kinopoisk.base.BaseFragment
import com.example.kinopoisk.databinding.FragmentFilmographyBinding
import com.example.kinopoisk.domain.onItemClick
import com.example.kinopoisk.entity.Movie
import com.google.android.material.chip.Chip

class FilmographyFragment : BaseFragment<FragmentFilmographyBinding>(FragmentFilmographyBinding::inflate) {

    private val filmographyAdapter = FilmographyAdapter { movie ->
        onItemClick(movie, this)
    }

    override fun onBindView() {
        super.onBindView()
        setupUIListeners()

        val actorName = arguments?.getString("actorName")
        binding.actorName.text = actorName

        val listFilms = when {
            Build.VERSION.SDK_INT >= 33 -> arguments?.getParcelableArrayList("filmography", Movie::class.java)
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

        val director = listFilms?.filter {
            it.professionKey == "DIRECTOR"
        }
        val writer = listFilms?.filter {
            it.professionKey == "WRITER"
        }
        val producer = listFilms?.filter {
            it.professionKey == "PRODUCER"
        }

        if (
            listFilms?.get(0)?.professionKey == "ACTOR" ||
            listFilms?.get(0)?.professionKey == "HERSELF" ||
            listFilms?.get(0)?.professionKey == "HIMSELF"
        ) {

            binding.chip1.isChecked = true
            binding.rvFilmography.adapter = filmographyAdapter
            filmographyAdapter.submitList(actor)


            binding.chip1.setOnClickListener {
                binding.rvFilmography.adapter = filmographyAdapter
                filmographyAdapter.submitList(actor)
                clearChipCheck(binding.chip2, binding.chip3)
                enabledChip1()
            }
            binding.chip2.setOnClickListener {
                binding.rvFilmography.adapter = filmographyAdapter
                filmographyAdapter.submitList(dubbing)
                clearChipCheck(binding.chip1, binding.chip3)
                enabledChip2()
            }
            binding.chip3.setOnClickListener {
                binding.rvFilmography.adapter = filmographyAdapter
                filmographyAdapter.submitList(himself)
                clearChipCheck(binding.chip1, binding.chip2)
                enabledChip3()
            }
        } else {
            binding.chip1.text = "Режиссер"
            binding.chip2.text = "Сценарист"
            binding.chip3.text = "Продюсер"

            binding.chip1.isChecked = true

            binding.rvFilmography.adapter = filmographyAdapter
            filmographyAdapter.submitList(director)

            binding.chip1.setOnClickListener {
                binding.rvFilmography.adapter = filmographyAdapter
                filmographyAdapter.submitList(director)
                clearChipCheck(binding.chip2, binding.chip3)
                enabledChip1()
            }
            binding.chip2.setOnClickListener {
                binding.rvFilmography.adapter = filmographyAdapter
                filmographyAdapter.submitList(writer)
                clearChipCheck(binding.chip1, binding.chip3)
                enabledChip2()
            }
            binding.chip3.setOnClickListener {
                binding.rvFilmography.adapter = filmographyAdapter
                filmographyAdapter.submitList(producer)
                clearChipCheck(binding.chip1, binding.chip2)
                enabledChip3()
            }
        }
    }

    private fun enabledChip3() {
        binding.chip3.isEnabled = false
        binding.chip1.isEnabled = true
        binding.chip3.isEnabled = true
    }

    private fun enabledChip2() {
        binding.chip2.isEnabled = false
        binding.chip1.isEnabled = true
        binding.chip3.isEnabled = true
    }

    private fun enabledChip1() {
        binding.chip1.isEnabled = false
        binding.chip2.isEnabled = true
        binding.chip3.isEnabled = true
    }

    private fun clearChipCheck(chip1: Chip, chip2: Chip) {
        chip1.isChecked = false
        chip2.isChecked = false
    }

    private fun setupUIListeners() {
        binding.arrowBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}