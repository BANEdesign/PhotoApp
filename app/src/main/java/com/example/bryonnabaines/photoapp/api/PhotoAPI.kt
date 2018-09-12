package com.example.bryonnabaines.photoapp.api

import com.example.bryonnabaines.photoapp.models.PhotoList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by bryonnabaines on 1/2/18.
 * retroFit (dependency, library) uses interfaces for the REST service
 * the authorization key and the call made to the api
 */
interface PhotoAPI {
    @GET("?key=7575669-5a629c0e8cf1aac094bba1fb7&q=nature&image_type=photo")
    fun getPhotos(@Query("page") page: Int, @Query("per_page") per_page: Int) : Call<PhotoList>
}