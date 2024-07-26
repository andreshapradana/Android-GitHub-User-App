package com.example.mygithubuser

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mygithubuser.data.response.UserItems
import com.example.mygithubuser.databinding.ItemUserBinding

class UserAdapter(private val onItemClick: (String) -> Unit) : ListAdapter<UserItems, UserAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }
    inner class MyViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserItems){
            Glide.with(binding.root.context)
                .load(user.avatarUrl)
                .into(binding.imageViewAvatar)

            binding.textViewUsername.text = user.login

            binding.root.setOnClickListener {
                onItemClick.invoke(user.login)
            }
        }
    }
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserItems>() {
            override fun areItemsTheSame(oldItem: UserItems, newItem: UserItems): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: UserItems, newItem: UserItems): Boolean {
                return oldItem == newItem
            }
        }
    }
}