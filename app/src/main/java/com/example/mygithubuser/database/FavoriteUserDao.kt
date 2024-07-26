package com.example.mygithubuser.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favoriteUser: FavoriteUser)
    @Query("SELECT * FROM FavoriteUser WHERE username = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser>
    @Delete
    fun delete(favoriteUser: FavoriteUser)
    @Query("SELECT * FROM FavoriteUser")
    fun getAllFavoriteUsers(): LiveData<List<FavoriteUser>>
}