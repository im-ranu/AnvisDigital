package com.anvisdigital.search

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.anvisdigital.R
import com.anvisdigital.search.adapter.SearchListAdapter
import com.anvisdigital.utils.Constants
import kotlinx.android.synthetic.main.activity_search_result.*

class SearchResultActivity : AppCompatActivity(),View.OnClickListener {


    var searchResponse : SearchResponse? = null
    var movieQuery : String? = null
    val TAG = SearchResultActivity::class.java.simpleName

    var movieList = ArrayList<SearchItem>()
    var adapter : SearchListAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)

        getIntentData()
        setUpViews()
        parseData()
    }

    private fun parseData() {
        if (searchResponse!=null){
            movieList.addAll(searchResponse?.search!!)
        }
        adapter?.notifyDataSetChanged()
    }

    private fun setUpViews() {
        iv_back.setOnClickListener(this)
        adapter = SearchListAdapter(this,movieList)
        rv_movie_list.layoutManager = GridLayoutManager(this,2)
        rv_movie_list.setHasFixedSize(true)
        rv_movie_list.adapter = adapter

    }

    private fun getIntentData() {

        searchResponse = intent.getSerializableExtra(Constants.SEARCH_RESULT) as SearchResponse
        movieQuery = intent.getStringExtra(Constants.MOVIE_NAME)


        txToolbarMovieName.setText(movieQuery)
        Log.e(TAG, searchResponse!!.response)
        Log.e(TAG, " "+searchResponse!!.search?.size)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.iv_back ->finish()
        }
    }
}