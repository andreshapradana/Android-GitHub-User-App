package com.example.mygithubuser.ui

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mygithubuser.data.response.DetailUserResponse
import com.example.mygithubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {

    private val _detailLiveData = MutableLiveData<DetailUserResponse>()
    val detailLiveData: LiveData<DetailUserResponse> = _detailLiveData

    private val _loadingLiveData = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean> = _loadingLiveData

    private val apiService = ApiConfig.getApiService()
    private val TAG = DetailViewModel::class.java.simpleName

    fun getDetailUser(username: String) {
        Log.d(TAG, "getDetailUser: Fetching user details for username: $username")
        _loadingLiveData.postValue(true)
        apiService.getDetailUser(username)
            .enqueue(object : Callback<DetailUserResponse> {
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                    _loadingLiveData.postValue(false)
                    if (response.isSuccessful) {
                        val user = response.body()
                        user?.let {
                            _detailLiveData.postValue(it)
                        }
                    } else {
                        Log.d(TAG, "getDetailUser: Fetching user details for username: $username")
                        Log.e(TAG, "onFailure: ${response.message()}")
                        // Handle error response
                    }
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    _loadingLiveData.postValue(false)
                    Log.d(TAG, "getDetailUser: Fetching user details for username: $username")
                    Log.e(TAG, "onFailure: ${t.message}")
                    // Handle failure
                }
            })
    }

}
