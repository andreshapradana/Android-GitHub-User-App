package com.example.mygithubuser.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mygithubuser.UserAdapter
import com.example.mygithubuser.data.response.UserItems
import com.example.mygithubuser.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var favoriteAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(
            FavoriteViewModel::class.java)



        setupRecyclerView()

        viewModel.getAllFavoriteUsers().observe(this) { users ->
            val items = arrayListOf<UserItems>()
            users?.forEach { user ->
                val item = user.avatarUrl?.let { UserItems(login = user.username, avatarUrl = it) }
                if (item != null) {
                    items.add(item)
                }
            }
            favoriteAdapter.submitList(items)
        }
    }

    private fun setupRecyclerView() {
        favoriteAdapter = UserAdapter { username ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("USERNAME", username)
            startActivity(intent)
        }
        binding.listUser.apply {
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
            adapter = favoriteAdapter
            addItemDecoration(DividerItemDecoration(this@FavoriteActivity, DividerItemDecoration.VERTICAL))
        }
    }
}
