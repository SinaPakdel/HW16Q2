package com.sina.maktab.data.network

import com.sina.maktab.data.model.FlickrResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrService {

    companion object {
        const val PER_PAGE: String = "100"
        const val JSON_CALL_BACK: String = "1"
        const val FORMAT: String = "json"
        const val EXTRAS: String = "url_s"
        const val USER_ID: String = "34427466731@N01"
        const val METHOD: String = "flickr.photos.getPopular"
        const val API_KEY: String = "1c04e05bce6e626247758d120b372a73"
        const val BASE_URL = "https://www.flickr.com/"
    }


    @GET("services/rest/")
    fun getAllImage(
        @Query("per_page") perPage: Int,
        @Query("page") page: Int
    ): Call<FlickrResponse>
}