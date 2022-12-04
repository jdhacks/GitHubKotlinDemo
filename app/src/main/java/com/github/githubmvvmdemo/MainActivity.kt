package com.github.githubmvvmdemo

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.github.githubmvvmdemo.Adapters.GitRepoAdapter
import com.github.githubmvvmdemo.ViewModels.RepoViewModel
import com.github.githubmvvmdemo.data.Item
import com.github.githubmvvmdemo.databinding.ActivityMainBinding
import com.github.githubmvvmdemo.utils.AppConstant
import com.github.githubmvvmdemo.utils.Utility

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    companion object{
         lateinit var viewModel: RepoViewModel

    }
    private lateinit var movieAdapter : GitRepoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showShimmer()
        viewModel = ViewModelProvider(this)[RepoViewModel::class.java]
        viewModel.getTrendingRepoList()
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                if (!s.isEmpty() && s != null) {
                    findInLiveData(s)
                } else {
                    viewModel.observLiveData().observe(this@MainActivity, Observer { repoList ->
                        movieAdapter.setRepoList(repoList as ArrayList<Item>)
                    })
                }
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                if (!s.isEmpty() && s != null) {
                    findInLiveData(s)
                } else {
                    viewModel.observLiveData().observe(this@MainActivity, Observer { repoList ->
                        movieAdapter.setRepoList(repoList as ArrayList<Item>)
                    })
                }
                return false
            }
        })

        Handler(Looper.getMainLooper()).postDelayed({
            prepareRecyclerView()

                        if(viewModel.ItemsLiveData.value!=null) {
                viewModel.observLiveData().observe(this, Observer { repoList ->
                    movieAdapter.setRepoList(repoList as ArrayList<Item>)
                })
                hideShimmer()
            }else{
                viewModel.observLiveData().observe(this, Observer { repoList ->
                    movieAdapter.setRepoList(repoList as ArrayList<Item>)
                })
                hideShimmer()
            }

        }, 3000)

    }

    fun showShimmer()  {
        binding.shimmer.startShimmer()
        binding.shimmer.visibility = View.VISIBLE
        //Utility.showProgress(getActivity());
        //Utility.showProgress(getActivity());
        binding.rvRepos.visibility = View.GONE
        binding.searchViewCard.visibility=View.GONE
    }
    fun hideShimmer()  {
        binding.shimmer.stopShimmer()
        binding.shimmer.visibility = View.GONE
        //Utility.showProgress(getActivity());
        //Utility.showProgress(getActivity());
        binding.rvRepos.visibility = View.VISIBLE
        binding.searchViewCard.visibility=View.VISIBLE
    }
    fun findInLiveData(key : String)  {
        var repoSearchlist : ArrayList<Item> = java.util.ArrayList<Item>()

        viewModel.observLiveData().observe(this, Observer { repoList ->
            for (user in repoList) {
                if (user.getOwner()!!.getLogin()!!.contains(key)) {
                    repoSearchlist.add(user)
                    viewModel.ItemsLiveSearchData.value=
                        repoSearchlist // return member when name found
                }
            }
        })
        if(repoSearchlist.size>0)
        {
            viewModel.ItemsLiveSearchData.observe(this@MainActivity, Observer { repoList ->
                movieAdapter.setRepoList(repoList as ArrayList<Item>)
            })
        }else{
            Utility.displayMessage(this,getString(R.string.lbl_no_data))
        }
    }

    private fun prepareRecyclerView() {
        movieAdapter = GitRepoAdapter(ArrayList<Item>(),this);
        binding.rvRepos.apply {
            layoutManager = GridLayoutManager(applicationContext,1)
            adapter = movieAdapter
        }
    }
}
