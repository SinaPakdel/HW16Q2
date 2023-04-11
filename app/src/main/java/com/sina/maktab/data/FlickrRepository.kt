package com.sina.maktab.data

import androidx.lifecycle.MutableLiveData
import com.sina.maktab.data.model.FlickrResponse
import com.sina.maktab.data.network.FlickrService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class FlickrRepository @Inject constructor(private val flickrService: FlickrService) {

    fun getFlickrImages(perPage: Int, page: Int): MutableLiveData<FlickrResponse> {
        val result = MutableLiveData<FlickrResponse>()
        flickrService.getAllImage(perPage, page).enqueue(object : Callback<FlickrResponse> {
            override fun onResponse(call: Call<FlickrResponse>, response: Response<FlickrResponse>) {
                result.value = response.body()
            }

            override fun onFailure(call: Call<FlickrResponse>, t: Throwable) {
            }
        })
        return result
    }
}

