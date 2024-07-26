package com.example.mygithubuser.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mygithubuser.FollowAdapter
import com.example.mygithubuser.data.response.ListFollowResponseItem
import com.example.mygithubuser.databinding.FragmentFollowersBinding


class FollowersFragment : Fragment() {

    private lateinit var binding: FragmentFollowersBinding
    private val viewModel: FollowViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val username = arguments?.getString(ARG_USERNAME)
        val position = arguments?.getInt(ARG_POSITION) ?: 0

        val layoutManager = LinearLayoutManager(requireContext())
        binding.listUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.listUser.addItemDecoration(itemDecoration)

        viewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            showLoading(isLoading)
        })

        if (position == 0) {
            viewModel.getFollowers(username ?: "")
        } else {
            viewModel.getFollowing(username ?: "")
        }

        viewModel.followerList.observe(viewLifecycleOwner, Observer { followerList ->
            setListUserData(followerList)
        })
    }

    private fun setListUserData(userList: List<ListFollowResponseItem>) {
        val adapter = FollowAdapter()
        adapter.submitList(userList)
        binding.listUser.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object {
        const val ARG_USERNAME = "username"
        const val ARG_POSITION = "position"
    }
}