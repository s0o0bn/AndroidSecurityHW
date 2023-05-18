package com.yoonsoobin.insecuremoviereview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yoonsoobin.insecuremoviereview.databinding.LayoutReviewItemBinding
import com.yoonsoobin.insecuremoviereview.vo.ReviewItem

class ReviewAdapter:
    ListAdapter<ReviewItem, ReviewAdapter.ReviewItemViewHolder>(DiffCallback) {
    inner class ReviewItemViewHolder(
        private var binding: LayoutReviewItemBinding
        ): RecyclerView.ViewHolder(binding.root) {
            fun bind(reviewItem: ReviewItem) {
                binding.movieTitle.text = reviewItem.title
                binding.movieScore.text = reviewItem.score.toString()
            }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<ReviewItem>() {
            override fun areItemsTheSame(oldItem: ReviewItem, newItem: ReviewItem): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: ReviewItem, newItem: ReviewItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewItemViewHolder {
        return ReviewItemViewHolder(
            LayoutReviewItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ReviewItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}