package com.example.radio.api.apimanager

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiManager {

    private var retrofit:Retrofit ? = null
    fun getRadioService():RadioService{
        retrofit = Retrofit.Builder()
            .baseUrl("https://mp3quran.net/api/v3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit!!.create(RadioService::class.java)
    }
}