package com.example.mygithubuser.data.retrofit

import com.example.mygithubuser.data.response.DetailUserResponse
import com.example.mygithubuser.data.response.ListFollowResponseItem
import com.example.mygithubuser.data.response.ListUserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun searchUsers(
        @Query("q") username: String
    ): Call<ListUserResponse>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<DetailUserResponse>
    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<ListFollowResponseItem>>
    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<ListFollowResponseItem>>
}