package com.example.mygithubuser.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mygithubuser.R
import com.example.mygithubuser.UserAdapter
import com.example.mygithubuser.data.response.UserItems
import com.example.mygithubuser.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var userAdapter: UserAdapter

    companion object {
        private const val USERNAME = "andreshapradana"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(
            MainViewModel::class.java)

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchBar.setText(searchView.text)
                    val tempInput = searchView.text.toString()
                    searchView.hide()
                    viewModel.searchUser(tempInput)
                    false
            }
        }

        val layoutManager = LinearLayoutManager(this)
        binding.listUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.listUser.addItemDecoration(itemDecoration)

        viewModel.userListLiveData.observe(this, { userList ->
            userList?.let { setListUserData(it) }
        })

        viewModel.loadingLiveData.observe(this, { isLoading ->
            showLoading(isLoading)
        })

        if (savedInstanceState == null) {
            viewModel.searchUser(USERNAME)
        }
    }

    private fun setListUserData(userList: List<UserItems>) {
        if (!::userAdapter.isInitialized) {
            userAdapter = UserAdapter { username ->
                val intent = Intent(this, DetailActivity::class.java)
                intent.putExtra("USERNAME", username)
                startActivity(intent)
            }
            binding.listUser.adapter = userAdapter
        }
        userAdapter.submitList(userList)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_favorite -> {
                startActivity(Intent(this, FavoriteActivity::class.java))
                true
            }
            R.id.menu_settings -> {
                startActivity(Intent(this, DarkModeActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}