package com.example.mygithubuser.ui

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mygithubuser.data.response.ListFollowResponseItem
import com.example.mygithubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel : ViewModel() {

    private val _followerList = MutableLiveData<List<ListFollowResponseItem>>()
    val followerList: LiveData<List<ListFollowResponseItem>> = _followerList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getFollowers(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object : Callback<List<ListFollowResponseItem>> {
            override fun onResponse(
                call: Call<List<ListFollowResponseItem>>,
                response: Response<List<ListFollowResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _followerList.value = response.body()
                }else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ListFollowResponseItem>>, t: Throwable) {
                _isLoading.value = false
            }
        })
    }

    fun getFollowing(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback<List<ListFollowResponseItem>> {
            override fun onResponse(
                call: Call<List<ListFollowResponseItem>>,
                response: Response<List<ListFollowResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _followerList.value = response.body()
                }else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ListFollowResponseItem>>, t: Throwable) {
                _isLoading.value = false
            }
        })
    }
}