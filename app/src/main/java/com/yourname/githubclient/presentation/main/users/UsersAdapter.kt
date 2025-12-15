package com.yourname.githubclient.presentation.main.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.yourname.githubclient.R
import com.yourname.githubclient.databinding.ItemUserBinding
import com.yourname.githubclient.domain.model.User

class UsersAdapter(
    private val onClick: (String,String) -> Unit
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
            binding.tvUsername.text = user.login
            binding.avatar.load(user.avatarUrl) {
                placeholder(R.drawable.ic_profile)
                error(R.drawable.ic_profile)
                fallback(R.drawable.ic_profile)
            }

            binding.root.setOnClickListener {
                onClick(user.login, user.avatarUrl)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(items[position])
}
