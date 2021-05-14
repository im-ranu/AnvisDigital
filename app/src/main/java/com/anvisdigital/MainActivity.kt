package com.anvisdigital

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.androiddevtask.network.ApiClient
import com.androiddevtask.network.ApiInterface
import com.anvisdigital.search.SearchResponse
import com.anvisdigital.search.SearchResultActivity
import com.anvisdigital.utils.Constants
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit

class MainActivity : AppCompatActivity(),View.OnClickListener {

    lateinit var apiInterface: ApiInterface
    var retrofit: Retrofit? = null
    var compositeDisposable: CompositeDisposable? = null
    val TAG = MainActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        setUpViews()
    }

    private fun setUpViews() {

        retrofit = ApiClient.getClient(this)
        if (retrofit!=null){
            apiInterface = retrofit!!.create(ApiInterface::class.java)
        }

        compositeDisposable = CompositeDisposable()
        bt_search.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.bt_search->{

                if (et_search_query.text.isBlank()){
                    Toast.makeText(this,"Movie Name can't be blank",Toast.LENGTH_SHORT).show()
                }else{
                    progressBar.visibility = View.VISIBLE
                    val movieName = et_search_query.text.toString()
                    compositeDisposable?.addAll(apiInterface.getMovieList(Constants.API_KEY,movieName)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<SearchResponse>(){
                            override fun onSuccess(t: SearchResponse?) {

                                if (t?.response.equals("True",ignoreCase = false)){
                                    progressBar.visibility = View.GONE
                                    val intent = Intent(this@MainActivity, SearchResultActivity::class.java)
                                    intent.putExtra(Constants.SEARCH_RESULT,t)
                                    intent.putExtra(Constants.MOVIE_NAME,movieName)
                                    startActivity(intent)
                                }else{
                                    progressBar.visibility = View.GONE
                                    Toast.makeText(this@MainActivity,"Movie not found",Toast.LENGTH_SHORT).show()
                                }

                            }

                            override fun onError(e: Throwable?) {

                                Log.e(TAG,e?.message.toString())
                            }

                        }))
                }


            }
        }
    }

}