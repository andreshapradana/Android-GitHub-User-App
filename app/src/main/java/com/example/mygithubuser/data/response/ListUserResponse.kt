package com.example.mygithubuser.data.response

import com.google.gson.annotations.SerializedName

data class ListUserResponse(

	@field:SerializedName("items")
	val userItems: List<UserItems>
)

data class UserItems(

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("login")
	val login: String
)
