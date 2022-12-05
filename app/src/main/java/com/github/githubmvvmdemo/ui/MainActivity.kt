package com.github.githubmvvmdemo.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.github.githubmvvmdemo.R
import com.github.githubmvvmdemo.adapters.GitRepoAdapter
import com.github.githubmvvmdemo.dataSources.remote.Item
import com.github.githubmvvmdemo.dataSources.remote.Owner
import com.github.githubmvvmdemo.databinding.ActivityMainBinding
import com.github.githubmvvmdemo.interfaces.AlertDialogCallback
import com.github.githubmvvmdemo.interfaces.ApiResponseCallback
import com.github.githubmvvmdemo.interfaces.ItemSelectionCallback
import com.github.githubmvvmdemo.utils.Utility
import com.github.githubmvvmdemo.viewModels.RepoViewModel

class MainActivity : AppCompatActivity(), ItemSelectionCallback, AlertDialogCallback,
    ApiResponseCallback {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: RepoViewModel

    private lateinit var repoAdapter: GitRepoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
         showShimmer()
        prepareRecyclerView()
        fetchData()

        //Delay to display shimmer
        Handler(Looper.getMainLooper()).postDelayed({
            setUpSearch()
            setDataInListView()
            hideShimmer()
        }, 2000)

    }

    private fun checkNetworkConnection() : Boolean{
        viewModel = ViewModelProvider(this)[RepoViewModel::class.java]

        viewModel.isNetworkAvailable = Utility.isInternetAvailable(this)

        return viewModel.isNetworkAvailable
    }

    private fun setDataInListView() {
        viewModel.itemsLiveData.observe(this@MainActivity) { repoList ->
            repoAdapter.setRepoList(repoList as ArrayList<Item>)
            hideShimmer()
        }
    }
    fun setSearchOperation(s: String) {
        if (s.isNotEmpty()) {
            findInLiveData(s)
        } else {
            setDataInListView()
        }
    }
    private fun setUpSearch() {

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                setSearchOperation(s)
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                setSearchOperation(s)
                return false
            }
        })

    }

    private fun fetchData() {
        if(checkNetworkConnection()) {
            viewModel.getTrendingRepoList(this)
        }else{
            Utility.nointernetAlertDialog(
                this,
                getString(R.string.no_internet),
                false,
                this)

        }

    }

    private fun showShimmer() {
        binding.shimmer.startShimmer()
        binding.shimmer.visibility = View.VISIBLE
        //Utility.showProgress(getActivity()) 
        //Utility.showProgress(getActivity()) 
        binding.rvRepos.visibility = View.GONE
        binding.searchViewCard.visibility = View.GONE
    }

    private fun hideShimmer() {
        binding.shimmer.stopShimmer()
        binding.shimmer.visibility = View.GONE
        //Utility.showProgress(getActivity()) 
        //Utility.showProgress(getActivity()) 
        binding.rvRepos.visibility = View.VISIBLE
        binding.searchViewCard.visibility = View.VISIBLE
    }

    //find the repo using name
    private fun findInLiveData(key: String) {
        val repoSearchlist: ArrayList<Item> = java.util.ArrayList<Item>()

        viewModel.itemsLiveData.observe(this) { repoList ->
            for (user in repoList) {
                if (user.getOwner()?.getLogin()?.contains(key) == true) {
                    repoSearchlist.add(user)
                    viewModel.itemsLiveSearchData.value =
                        repoSearchlist // return member when name found
                }
            }
        }
        if (repoSearchlist.size > 0) {
            viewModel.itemsLiveSearchData.observe(this@MainActivity) { repoList ->
                repoAdapter.setRepoList(repoList as ArrayList<Item>)
            }
        } else {
            Utility.displayMessage(this, getString(R.string.lbl_no_data))
        }
    }


    private fun prepareRecyclerView() {
        repoAdapter = GitRepoAdapter(ArrayList(), this, this)
        binding.rvRepos.apply {
            layoutManager = GridLayoutManager(applicationContext, 1)
            adapter = repoAdapter
        }
    }

    override fun onClick(item: Owner?, position: Int) {
        if (item?.getSelected() == true) {
            viewModel.itemsLiveData.value?.get(position)?.getOwner()?.setSelected(false)
            item.setSelected(false)
        } else {
            viewModel.itemsLiveData.value?.get(position)?.getOwner()?.setSelected(true)
            item?.setSelected(true)
        }
        repoAdapter.notifyItemChanged(position)
    }

    override fun onOkClick() {
        //dismiss dialog
    }

    override fun onRetryClick() {
        showShimmer()
        fetchData()
        setDataInListView()
    }

    override fun onSuccess() {
       Utility.displayMessage(this,"Enjoy browsing")
    }

    override fun onFailed(errorMessage : String) {
        Utility.alertDialog(
            this,
            errorMessage,
            false,
            this)
    }


}
