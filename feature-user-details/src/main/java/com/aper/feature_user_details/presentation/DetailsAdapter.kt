package com.aper.feature_user_details.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aper.core.model.Repository
import com.aper.feature_user_details.databinding.ItemRepoBinding

class DetailsAdapter : RecyclerView.Adapter<DetailsAdapter.ViewHolder>() {

    private val items = mutableListOf<Repository>()

    fun submitList(newItems: List<Repository>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged() // classic RecyclerView approach
    }

    inner class ViewHolder(
        private val binding: ItemRepoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Repository) {
            binding.tvRepoName.text = item.name
            binding.tvRepoDescription.text = item.description ?: "No description"
            binding.tvRepoLanguage.text = item.language ?: "Unknown"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRepoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
