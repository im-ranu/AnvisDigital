package com.androiddevtask.network



import com.anvisdigital.movie_details.MovieDetailsResponse
import com.anvisdigital.search.SearchResponse

import io.reactivex.rxjava3.core.Single
import retrofit2.http.*


interface ApiInterface {



    @GET("?")
    fun getMovieList(@Query("apiKey") apiKey : String,@Query("s") movieName : String) : Single<SearchResponse>

    @GET("?")
    fun getMovieDetails(@Query("apiKey") apiKey : String,@Query("i") movieID : String) : Single<MovieDetailsResponse>


}