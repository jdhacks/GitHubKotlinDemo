package com.github.githubmvvmdemo.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.githubmvvmdemo.R
import com.github.githubmvvmdemo.dataSources.remote.Item
import com.github.githubmvvmdemo.dataSources.remote.Owner
import com.github.githubmvvmdemo.databinding.ItemSearchRepoBinding
import com.github.githubmvvmdemo.interfaces.ItemSelectionCallback
import com.github.githubmvvmdemo.ui.MainActivity
import com.github.githubmvvmdemo.utils.Utility
import com.google.gson.Gson

class GitRepoAdapter(
    repoList : ArrayList<Item>,
    itemSelectionCallback: ItemSelectionCallback,
    fragmentActivity: AppCompatActivity
)  :
    RecyclerView.Adapter<GitRepoAdapter.MyViewHolder>()  {
    private var repoList: ArrayList<Item>
    private var itemSelectionCallback: ItemSelectionCallback
    private var context: Context? = null
    private val fragmentActivity: AppCompatActivity
    fun setRepoList(repoList: ArrayList<Item>) {
        this.repoList = repoList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        context = parent.context
        return MyViewHolder(
            ItemSearchRepoBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            ).root
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item: Owner? = repoList[position].getOwner()
        Log.d("okhttp", "oneshot: response" + Gson().toJson(item))
        if (item?.getSelected() == true) {
            holder.imgSelected.setImageResource(R.drawable.ic_selected)
        } else {
            holder.imgSelected.setImageResource(R.drawable.ic_unselected)
        }
        holder.imgSelected.setOnClickListener {

            itemSelectionCallback.onClick(item, holder.adapterPosition)

        }
        holder.carditem.setOnLongClickListener {
            itemSelectionCallback.onClick(item , holder.adapterPosition)
            false
        }
        holder.txtUserName.setText(item?.getLogin())
        if (item?.getAvatarUrl() != null) Glide.with(context!!)
            .load(item.getAvatarUrl())
            .placeholder(Utility.getCircularProgressDrawable(context!!))
            .error(R.drawable.ic_user_img)
            .apply(RequestOptions().fitCenter())
            .apply(RequestOptions()).into(holder.imgUser)
    }

    override fun getItemCount(): Int {
        return repoList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgUser: ImageView
        val imgSelected: ImageView
        val txtUserName: AppCompatTextView
        var root1: ConstraintLayout
        var carditem: CardView

        init {
            imgSelected = itemView.findViewById(R.id.selectedImg)
            imgUser = itemView.findViewById(R.id.imgUser)
            txtUserName = itemView.findViewById(R.id.txtUserName)
            root1 = itemView.findViewById(R.id.root1)
            carditem = itemView.findViewById(R.id.cardView1)
        }
    }

    
    init {

        this.itemSelectionCallback= itemSelectionCallback
        this.repoList = repoList
        this.fragmentActivity = fragmentActivity
    }
}