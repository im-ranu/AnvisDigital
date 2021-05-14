package com.anvisdigital.movie_details

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.androiddevtask.network.ApiClient
import com.androiddevtask.network.ApiInterface
import com.anvisdigital.R
import com.anvisdigital.search.SearchItem
import com.anvisdigital.utils.Constants
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.content_scrolling.*
import retrofit2.Retrofit

class Details : AppCompatActivity() {

    lateinit var apiInterface: ApiInterface
    var retrofit: Retrofit? = null
    var compositeDisposable: CompositeDisposable? = null

    var movieItem : SearchItem? = null
    val TAG = Details::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        getIntentData()
        setUpToolbar()
        setUpViews()

        loadData()
    }

    private fun setUpToolbar() {


            setSupportActionBar(toolbar)
            iv_back_detail_activity.setOnClickListener{
                onBackPressed()
            }
    }

    private fun loadData() {
        compositeDisposable?.addAll(
            apiInterface.getMovieDetails(Constants.API_KEY,movieItem!!.imdbID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<MovieDetailsResponse>(){
                    override fun onSuccess(t: MovieDetailsResponse?) {
                        tx_movie_plot.setText(t?.plot)

                    }

                    override fun onError(e: Throwable?) {
                        Log.e(TAG,e?.message.toString())
                    }

                })
        )
    }

    private fun setUpViews() {
        retrofit = ApiClient.getClient(this)
        if (retrofit!=null){
            apiInterface = retrofit!!.create(ApiInterface::class.java)
        }

        compositeDisposable = CompositeDisposable()
    }

    private fun getIntentData() {
        movieItem = intent.getSerializableExtra(Constants.MOVIE_ITEM) as SearchItem

        iv_movie_image.load(movieItem?.poster)
        collapsetoolbar_tx_movie_name.setText(movieItem?.title)
        collapsetoolbar_tx_release_year.setText("Release Year : ${movieItem?.year}")

    }
}