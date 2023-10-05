package com.example.kinopoisk.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.kinopoisk.R
import com.example.kinopoisk.databinding.FragmentOnBoardingBinding
import com.google.android.material.tabs.TabLayoutMediator


class OnBoardingFragment : Fragment() {

    private var _binding: FragmentOnBoardingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOnBoardingBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val items = listOf(
            ListOnBoarding("Узнавай\nо премьерах",R.drawable.onboarding_start_image),
            ListOnBoarding("Создавай\nколлекции",R.drawable.onboarding_second_image),
            ListOnBoarding("Делись\nс друзьями",R.drawable.onboarding_third_image)
        )
        binding.viewPager.adapter = OnBoardingAdapter(items)

        binding.skipButton.setOnClickListener {
            findNavController().navigate(R.id.action_onBoardingFragment_to_homeFragment)
        }
        TabLayoutMediator(binding.tab,binding.viewPager){ _,_-> }.attach()
    }
}