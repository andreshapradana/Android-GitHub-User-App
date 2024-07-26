package com.example.mygithubuser.ui

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.mygithubuser.R
import com.example.mygithubuser.SectionsPagerAdapter
import com.example.mygithubuser.data.response.DetailUserResponse
import com.example.mygithubuser.database.FavoriteUser
import com.example.mygithubuser.databinding.ActivityDetailBinding
import com.example.mygithubuser.repository.FavoriteUserRepository
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel
    private lateinit var favoriteUserRepository: FavoriteUserRepository
    private lateinit var favoriteUserLiveData: LiveData<FavoriteUser>

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]

        val username = intent.getStringExtra("USERNAME")
        Log.d(TAG,"getDetailUser: Fetching user details for username: $username")
        viewModel.getDetailUser(username ?: "")

        viewModel.detailLiveData.observe(this) { user ->
            user?.let {
                setDetail(it)
            }
        }

        viewModel.loadingLiveData.observe(this) { isLoading ->
            showLoading(isLoading)
        }

        val sectionsPagerAdapter = username?.let { SectionsPagerAdapter(this, it) }
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

        favoriteUserRepository = FavoriteUserRepository(application)

        binding.fabAdd.setOnClickListener {
            favoriteClick()
        }

        favoriteUserLiveData = favoriteUserRepository.getFavoriteUserByUsername(username.toString())
        favoriteUserLiveData.observe(this, Observer { favoriteUser ->
            if (favoriteUser != null) {
                binding.fabAdd.setImageResource(R.drawable.ic_favorite)
            } else {
                binding.fabAdd.setImageResource(R.drawable.ic_favorite_border)
            }
        })

    }

    private fun favoriteClick() {
        val username = intent.getStringExtra("USERNAME") ?: return
        val avatarUrl = viewModel.detailLiveData.value?.avatarUrl ?: return // Assuming avatarUrl is accessible from DetailViewModel

        // Check if the user is already in favorites
        val favoriteUser = favoriteUserLiveData.value
        if (favoriteUser != null) {
            // User is already in favorites, perform delete action
            favoriteUserRepository.delete(favoriteUser)
            // Optionally, provide user feedback (e.g., toast message)
            Toast.makeText(this, "Removed from favorites", Toast.LENGTH_SHORT).show()
        } else {
            // User is not in favorites, perform insert action
            val newFavoriteUser = FavoriteUser(username, avatarUrl)
            favoriteUserRepository.insert(newFavoriteUser)
            // Optionally, provide user feedback (e.g., toast message)
            Toast.makeText(this, "Added to favorites", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setDetail(user: DetailUserResponse) {
        binding.apply {
            Glide.with(this@DetailActivity)
                .load(user.avatarUrl)
                .into(imageViewAvatar)

            tvUsernameDetail.text = user.login ?: ""
            tvnameDetail.text = user.name ?: ""
            tvFollowersCount.text = getString(R.string.followers_count, user.followers ?: 0)
            tvFollowingCount.text = getString(R.string.following_count, user.following ?: 0)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}