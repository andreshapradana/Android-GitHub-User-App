package com.example.mygithubuser.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.mygithubuser.database.FavoriteUser
import com.example.mygithubuser.repository.FavoriteUserRepository

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private val mFavoriteUserRepository: FavoriteUserRepository = FavoriteUserRepository(application)
    fun getAllFavoriteUsers(): LiveData<List<FavoriteUser>> = mFavoriteUserRepository.getAllFavoriteUsers()
}