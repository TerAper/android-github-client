package com.yourname.githubclient.presentation.main.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.yourname.githubclient.databinding.ItemUserBinding
import com.yourname.githubclient.domain.model.User

class UsersAdapter(
    private val onClick: (User) -> Unit
) : RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    private val items = mutableListOf<User>()

    fun submitList(new: List<User>) {
        items.clear()
        items.addAll(new)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            binding.tvUsername.text = user.username
            binding.avatar.load(user.avatarUrl)

            binding.root.setOnClickListener { onClick(user) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(items[position])
}
