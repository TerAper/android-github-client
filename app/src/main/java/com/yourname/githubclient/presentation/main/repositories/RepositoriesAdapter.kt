package com.yourname.githubclient.presentation.main.repositories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yourname.githubclient.databinding.ItemRepositoryBinding
import com.yourname.githubclient.domain.model.Repository

class RepositoriesAdapter : RecyclerView.Adapter<RepositoriesAdapter.Holder>() {

    private val data = mutableListOf<Repository>()

    fun submitList(list: List<Repository>) {
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }

    inner class Holder(private val binding: ItemRepositoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Repository) {
            binding.txtRepoName.text = item.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemRepositoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size
}
