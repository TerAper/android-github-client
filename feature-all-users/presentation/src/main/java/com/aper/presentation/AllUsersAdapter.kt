package com.aper.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.aper.domain.model.AllUsersUser
import com.aper.feature_all_users.presentation.R
import com.aper.feature_all_users.presentation.databinding.ItemUserBinding


class AllUsersAdapter(
    private val onClick: (String, String) -> Unit
) : RecyclerView.Adapter<AllUsersAdapter.ViewHolder>() {

    private val items = mutableListOf<AllUsersUser>()

    fun submitInitial(list: List<AllUsersUser>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(
        private val binding: ItemUserBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: AllUsersUser) {
            binding.tvUsername.text = user.login
            binding.avatar.load(user.avatarUrl) {
                placeholder(R.drawable.placeholder)
                error(R.drawable.placeholder)
            }

            binding.root.setOnClickListener {
                onClick(user.login, user.avatarUrl)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemUserBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
