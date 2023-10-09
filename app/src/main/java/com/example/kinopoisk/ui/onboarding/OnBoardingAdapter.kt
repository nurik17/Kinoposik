package com.example.kinopoisk.ui.onboarding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kinopoisk.databinding.OnboardingItemsBinding

class OnBoardingAdapter(private val items : List<ListOnBoarding>
) : RecyclerView.Adapter<OnBoardingAdapter.OnBoardingViewHolder>(){

    class OnBoardingViewHolder(private val binding: OnboardingItemsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ListOnBoarding) {
            binding.text.text = item.text
            Glide.with(binding.mainImage.context)
                .load(item.id)
                .into(binding.mainImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = OnBoardingViewHolder(
        OnboardingItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: OnBoardingViewHolder, position: Int) {
        holder.bind(items[position])
    }

}
