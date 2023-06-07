package com.example.kinopoisk.ui.w

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.kinopoisk.R
import com.example.kinopoisk.databinding.FragmentFirstBinding


class FirstFragment : Fragment() {

    companion object {
        const val KIND_KEY = "KIND_KEY"
        fun newInstance(kind: OnBoardingKind) = FirstFragment().apply {
            arguments = bundleOf(KIND_KEY to kind)
        }
    }

    private var _binding : FragmentFirstBinding? = null
    private val binding get() = _binding!!


    private val kind by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            (arguments?.getParcelable(KIND_KEY, OnBoardingKind::class.java))
        } else {
            (arguments?.getParcelable(KIND_KEY) as? OnBoardingKind)
        } ?: throw java.lang.RuntimeException("$KIND_KEY shouldn't be nullable")
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when(kind){
            OnBoardingKind.FIRST->{
                binding.image.setImageResource(R.drawable.onb1)
                binding.textTitle.text = "Узнавай\nо премьерах"
            }
            OnBoardingKind.SECOND->{
                binding.image.setImageResource(R.drawable.onb2)
                binding.textTitle.text = "Создавай\nколлекции"
            }
            OnBoardingKind.THIRD->{
                binding.image.setImageResource(R.drawable.onb3)
                binding.textTitle.text = "Делись\nс друзьями"

            }
        }


    }
}