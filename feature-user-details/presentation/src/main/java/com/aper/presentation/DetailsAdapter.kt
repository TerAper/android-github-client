package com.aper.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aper.domain.model.UserRepos
import com.aper.feature_user_details.presentation.databinding.ItemRepoBinding

class DetailsAdapter : RecyclerView.Adapter<DetailsAdapter.ViewHolder>() {

    private val items = mutableListOf<UserRepos>()

    fun submitList(newItems: List<UserRepos>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged() // classic RecyclerView approach
    }

    inner class ViewHolder(
        private val binding: ItemRepoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: UserRepos) {
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
