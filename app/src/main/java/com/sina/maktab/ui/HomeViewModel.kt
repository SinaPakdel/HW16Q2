package com.sina.maktab.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sina.maktab.data.FlickrRepository
import com.sina.maktab.data.model.FlickrResponse
import com.sina.maktab.data.network.FlickrService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val flickrService: FlickrService) : ViewModel() {
    private val TAG = "HomeViewModel"
    private val _photo = MutableLiveData<FlickrResponse>()
    val photo: LiveData<FlickrResponse> get() = _photo


    fun getImage(perPage: Int, page: Int) = flickrService.getAllImage(perPage, page).enqueue(object : Callback<FlickrResponse> {
        override fun onResponse(call: Call<FlickrResponse>, response: Response<FlickrResponse>) {
            Log.e(TAG, "onResponse: ${response.body()}", )
            _photo.postValue(response.body())
        }

        override fun onFailure(call: Call<FlickrResponse>, t: Throwable) {
            Log.e(TAG, "onFailure: ${t.message}")
        }
    })
}