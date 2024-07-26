package com.example.mygithubuser.ui

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mygithubuser.data.response.ListUserResponse
import com.example.mygithubuser.data.response.UserItems
import com.example.mygithubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _userListLiveData = MutableLiveData<List<UserItems>>()
    val userListLiveData: LiveData<List<UserItems>> = _userListLiveData

    private val _loadingLiveData = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean> = _loadingLiveData

    fun searchUser(query: String) {
        _loadingLiveData.value = true
        ApiConfig.getApiService().searchUsers(query)
            .enqueue(object : Callback<ListUserResponse> {
                override fun onResponse(
                    call: Call<ListUserResponse>,
                    response: Response<ListUserResponse>
                ) {
                    _loadingLiveData.value = false
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        responseBody?.let {
                            _userListLiveData.value = it.userItems
                        }
                    } else {
                        Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<ListUserResponse>, t: Throwable) {
                    _loadingLiveData.value = false
                }
            })
    }
}