package com.example.radio

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.radio.api.apimanager.ApiManager
import com.example.radio.api.model.IzaaResponse
import com.example.radio.api.model.RadiosItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RadioViewModel:ViewModel() {


    val izaaTv: MutableLiveData<String?> = MutableLiveData()
    var radios: MutableLiveData<List<RadiosItem?>?> = MutableLiveData()
    val errorMessage = MutableLiveData<String>()
    val toastMessage = MutableLiveData<String>()
    lateinit var mediaPlayer:MediaPlayer
    val playVisibility = MutableLiveData<Int>()
    val pauseVisibility = MutableLiveData<Int>()
    val progressBar = MutableLiveData<Int>()
    var currentChannel = 0


    fun playIzaa(){
        if (::mediaPlayer.isInitialized) {
            mediaPlayer.start()
            toastMessage.postValue("Playing: ${radios.value?.get(currentChannel)?.name}")
            progressBar.postValue(View.GONE)
            playVisibility.postValue(View.GONE)
            pauseVisibility.postValue(View.VISIBLE)
        } else {
            toastMessage.postValue("Media is not ready yet.")
        }

    }

    fun pauseIzaa(){
        if (::mediaPlayer.isInitialized && mediaPlayer.isPlaying) {
            mediaPlayer.pause()
            toastMessage.postValue("Paused: ${radios.value?.get(currentChannel)?.name}")
            pauseVisibility.postValue(View.GONE)
            playVisibility.postValue(View.VISIBLE)
        }

    }

    fun nextChannel(){
        progressBar.postValue(View.VISIBLE)
        pauseVisibility.postValue(View.GONE)
        playVisibility.postValue(View.GONE)
        if (::mediaPlayer.isInitialized) {
            mediaPlayer.stop()
            mediaPlayer.reset()
        }
        if(currentChannel==0 || currentChannel < radios.value!!.size-1){
            ++currentChannel
            playMediaPlayer()
        }
        else if(currentChannel==radios.value?.size!!-1){
            currentChannel=0
            playMediaPlayer()
        }
    }

    fun prevChannel(){
        progressBar.postValue(View.VISIBLE)
        pauseVisibility.postValue(View.GONE)
        playVisibility.postValue(View.GONE)
        if (::mediaPlayer.isInitialized) {
            mediaPlayer.stop()
            mediaPlayer.reset()
        }
        if(currentChannel==0){
            currentChannel = (radios.value!!.size)-1
            playMediaPlayer()
        }
        else{
            --currentChannel
            playMediaPlayer()
        }

    }



    fun loadRadios(){
        progressBar.postValue(View.VISIBLE)
        pauseVisibility.postValue(View.GONE)
        playVisibility.postValue(View.GONE)
        ApiManager.getRadioService().getChannels().enqueue(object :Callback<IzaaResponse>{
            override fun onResponse(call: Call<IzaaResponse>, response: Response<IzaaResponse>) {
                if(response.isSuccessful){
                    radios.value = response.body()?.radios
                    playMediaPlayer()
                    toastMessage.postValue("The media is ready")
                }
                else{
                    errorMessage.postValue("Error: ${response.code()} - ${response.message()}")
                }
            }

            override fun onFailure(p0: Call<IzaaResponse>, t: Throwable) {
                errorMessage.postValue("Failure: ${t.localizedMessage}")
            }

        })
    }
    fun playMediaPlayer(){
        mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            setDataSource(radios.value?.get(currentChannel)?.url)
            prepareAsync()
            setOnPreparedListener {
                izaaTv.postValue(radios.value?.get(currentChannel)?.name)
                progressBar.postValue(View.GONE)
                pauseVisibility.postValue(View.GONE)
                playVisibility.postValue(View.VISIBLE)
            }
        }
    }


}