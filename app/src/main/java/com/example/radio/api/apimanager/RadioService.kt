package com.example.radio.api.apimanager

import com.example.radio.api.model.IzaaResponse
import retrofit2.Call
import retrofit2.http.GET

interface  RadioService {

    @GET("radios")
    fun getChannels():Call<IzaaResponse>
}