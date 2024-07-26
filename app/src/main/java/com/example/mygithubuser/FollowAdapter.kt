package com.example.mygithubuser

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mygithubuser.data.response.ListFollowResponseItem
import com.example.mygithubuser.databinding.ItemUserBinding

class FollowAdapter : ListAdapter<ListFollowResponseItem, FollowAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    inner class MyViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: ListFollowResponseItem){
            Glide.with(binding.root.context)
                .load(user.avatarUrl)
                .into(binding.imageViewAvatar)

            binding.textViewUsername.text = user.login
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListFollowResponseItem>() {
            override fun areItemsTheSame(oldItem: ListFollowResponseItem, newItem: ListFollowResponseItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ListFollowResponseItem, newItem: ListFollowResponseItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}