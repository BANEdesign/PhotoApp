package com.example.bryonnabaines.photoapp.api

import com.example.bryonnabaines.photoapp.models.PhotoList
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by bryonnabaines on 1/2/18.
 *  sets up a line of contact with pixabay
 *  communicates with the API service
 */
class PhotoRetriever {

    private val service: PhotoAPI

    init {
        val retrofit = Retrofit.Builder().
                baseUrl("https://pixabay.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        service = retrofit.create(PhotoAPI::class.java)
    }

    fun getPhotos(callback : Callback<PhotoList>){
        val call = service.getPhotos()
        call.enqueue(callback)
    }
}