package com.anvisdigital.search.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.anvisdigital.movie_details.Details
import com.anvisdigital.R
import com.anvisdigital.search.SearchItem
import com.anvisdigital.utils.Constants

class SearchListAdapter(var mContext : Context,var movieList : List<SearchItem>) : RecyclerView.Adapter<SearchListAdapter.ViewHolder>() {
    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){


        var moviePoster = itemView.findViewById<ImageView>(R.id.movieImage)
        var movieName = itemView.findViewById<TextView>(R.id.txMovieName)
        var releaseYear = itemView.findViewById<TextView>(R.id.txReleaseYear)
        var itemLayout = itemView.findViewById<ConstraintLayout>(R.id.itemLayout)

        fun bindItem(searchItem: SearchItem){

            moviePoster.load(searchItem.poster)
            releaseYear.setText("Release Year : ${searchItem.year}")
            movieName.setText(searchItem.title)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie_grid,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val movieItem = movieList.get(position)

        holder.bindItem(movieItem)
        holder.itemLayout.setOnClickListener {
            val intent = Intent(mContext, Details::class.java)
            intent.putExtra(Constants.MOVIE_ITEM,movieItem)
            mContext.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return movieList.size
    }
}