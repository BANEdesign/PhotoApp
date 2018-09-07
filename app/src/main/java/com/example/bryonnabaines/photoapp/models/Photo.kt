package com.example.bryonnabaines.photoapp.models

import java.io.Serializable

/**
 * Created by bryonnabaines on 1/2/18.
 * the model for the photos retrived from the api
 */
data class Photo(val id : String,
                 val likes: Int?,
                 val favorites: Int?,
                 val tags : String,
                 val previewURL : String,
                 val webFormatURL: String) : Serializable