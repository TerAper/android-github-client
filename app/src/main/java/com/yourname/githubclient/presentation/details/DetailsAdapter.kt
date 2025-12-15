package com.yourname.githubclient.presentation.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yourname.githubclient.databinding.ItemRepoBinding
import com.yourname.githubclient.domain.model.Repository

class RepoAdapter : RecyclerView.Adapter<RepoAdapter.ViewHolder>() {

    private val items = mutableListOf<Repository>()

    fun submitList(newList: List<Repository>) {
        items.clear()
        items.addAll(newList)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemRepoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Repository) {
            binding.tvRepoName.text = item.name
            binding.tvRepoDescription.text = item.description ?: "No description"
            binding.tvRepoLanguage.text = item.language ?: "Unknown"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            ItemRepoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(items[position])
}
