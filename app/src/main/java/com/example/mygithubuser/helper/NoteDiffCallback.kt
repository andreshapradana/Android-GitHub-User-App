package com.example.mygithubuser.helper

import androidx.recyclerview.widget.DiffUtil
import com.example.mygithubuser.database.FavoriteUser

class NoteDiffCallback(private val oldFavoriteUserList: List<FavoriteUser>, private val newFavoriteUserList: List<FavoriteUser>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldFavoriteUserList.size
    override fun getNewListSize(): Int = newFavoriteUserList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldFavoriteUserList[oldItemPosition] == newFavoriteUserList[newItemPosition]
    }
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldFavoriteUser = oldFavoriteUserList[oldItemPosition]
        val newFavoriteUser = newFavoriteUserList[newItemPosition]
        return oldFavoriteUser.username == newFavoriteUser.username && oldFavoriteUser.avatarUrl == newFavoriteUser.avatarUrl
    }
}