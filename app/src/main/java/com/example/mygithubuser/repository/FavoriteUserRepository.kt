package com.example.mygithubuser.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.mygithubuser.database.FavoriteUser
import com.example.mygithubuser.database.FavoriteUserDao
import com.example.mygithubuser.database.FavoriteUserRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteUserRepository(application: Application) {
    private val mFavUserDao: FavoriteUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteUserRoomDatabase.getDatabase(application)
        mFavUserDao = db.favUserDao()
    }

    fun insert(favoriteUser: FavoriteUser) {
        executorService.execute { mFavUserDao.insert(favoriteUser) }
    }

    fun getFavoriteUserByUsername(username : String): LiveData<FavoriteUser> = mFavUserDao.getFavoriteUserByUsername(username)

    fun delete(favoriteUser: FavoriteUser){
        executorService.execute {mFavUserDao.delete(favoriteUser)}
    }

    fun getAllFavoriteUsers(): LiveData<List<FavoriteUser>> {
        return mFavUserDao.getAllFavoriteUsers()
    }
}