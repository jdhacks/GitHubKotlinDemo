package com.github.githubmvvmdemo.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.github.githubmvvmdemo.adapters.GitRepoAdapter
import com.github.githubmvvmdemo.R
import com.github.githubmvvmdemo.viewModels.RepoViewModel
import com.github.githubmvvmdemo.dataSources.remote.Item
import com.github.githubmvvmdemo.dataSources.remote.Owner
import com.github.githubmvvmdemo.databinding.ActivityMainBinding
import com.github.githubmvvmdemo.interfaces.ItemSelectionCallback
import com.github.githubmvvmdemo.utils.Utility

class MainActivity : AppCompatActivity() ,  ItemSelectionCallback {
    lateinit var binding : ActivityMainBinding
    companion object{
         lateinit var viewModel: RepoViewModel

    }
    private lateinit var repoAdapter : GitRepoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showShimmer()
        prepareRecyclerView()
        Handler(Looper.getMainLooper()).postDelayed({
                  FetchDataAndSetData()
                  SetUpSearch()
        }, 3000)

    }
    fun SetDataInListView()  {
        viewModel.observLiveData().observe(this@MainActivity, { repoList ->
            repoAdapter.setRepoList(repoList as ArrayList<Item>)
        })
    }
    fun SetUpSearch()  {

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                if (!s.isEmpty()) {
                    findInLiveData(s)
                } else {
                    SetDataInListView()
                }
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                if (!s.isEmpty()) {
                    findInLiveData(s)
                } else {
                    SetDataInListView()
                }
                return false
            }
        })

    }

    fun FetchDataAndSetData()  {

        viewModel = ViewModelProvider(this)[RepoViewModel::class.java]
        viewModel.getTrendingRepoList()

        if(viewModel.itemsLiveData.value!=null) {
            SetDataInListView()
            hideShimmer()
        }

    }
    fun showShimmer()  {
        binding.shimmer.startShimmer()
        binding.shimmer.visibility = View.VISIBLE
        //Utility.showProgress(getActivity()) 
        //Utility.showProgress(getActivity()) 
        binding.rvRepos.visibility = View.GONE
        binding.searchViewCard.visibility=View.GONE
    }
    fun hideShimmer()  {
        binding.shimmer.stopShimmer()
        binding.shimmer.visibility = View.GONE
        //Utility.showProgress(getActivity()) 
        //Utility.showProgress(getActivity()) 
        binding.rvRepos.visibility = View.VISIBLE
        binding.searchViewCard.visibility=View.VISIBLE
    }
    //find the repo using name
    fun findInLiveData(key : String)  {
        val repoSearchlist : ArrayList<Item> = java.util.ArrayList<Item>()

        viewModel.observLiveData().observe(this, { repoList ->
            for (user in repoList) {
                if (user.getOwner()?.getLogin()?.contains(key) == true) {
                    repoSearchlist.add(user)
                    viewModel.itemsLiveSearchData.value=
                        repoSearchlist // return member when name found
                }
            }
        })
        if(repoSearchlist.size>0)
        {
            viewModel.itemsLiveSearchData.observe(this@MainActivity, { repoList ->
                repoAdapter.setRepoList(repoList as ArrayList<Item>)
            })
        }else{
            Utility.displayMessage(this,getString(R.string.lbl_no_data))
        }
    }

   
    private fun prepareRecyclerView() {
        repoAdapter = GitRepoAdapter(ArrayList<Item>(),this,this)
        binding.rvRepos.apply {
            layoutManager = GridLayoutManager(applicationContext,1)
            adapter = repoAdapter
        }
    }

    override fun onClick(item: Owner?, position: Int) {
        if (item?.getSelected()==true) {
            viewModel.itemsLiveData.value?.get(position)?.getOwner()?.setSelected(true)
            item?.setSelected(false)
        } else {
            viewModel.itemsLiveData.value?.get(position)?.getOwner()?.setSelected(true)
            item?.setSelected(false)
        }
        repoAdapter.notifyItemChanged(position)
    }


}
