package com.example.kinopoisk.ui.w

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kinopoisk.databinding.FragmentOnBoardingBinding


class OnBoardingFragment : Fragment() {

    private var _binding : FragmentOnBoardingBinding?=null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOnBoardingBinding.inflate(inflater,container,false)

        val fragmentList = arrayListOf<Fragment>(
            FirstFragment.newInstance(OnBoardingKind.FIRST),
            FirstFragment.newInstance(OnBoardingKind.SECOND),
            FirstFragment.newInstance(OnBoardingKind.THIRD)
        )

        val adapter = ViewPagerAdapter(
            fragmentList, requireActivity().supportFragmentManager,
            lifecycle
        )

        val viewPager = binding.viewpager
        viewPager.adapter = adapter
        val indicator = binding.dotsIndicator
        indicator.attachTo(viewPager)
        return binding.root
    }

}