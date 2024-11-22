package com.example.radio.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.radio.R
import com.example.radio.api.model.RadiosItem

class ChannelAdapter(private val channelList: MutableLiveData<List<RadiosItem?>?>) :
    RecyclerView.Adapter<ChannelAdapter.ChannelViewHolder>() {

    class ChannelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val channelName: TextView = itemView.findViewById(R.id.channel_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_channel, parent, false)
        return ChannelViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChannelViewHolder, position: Int) {
        val channel = channelList.value?.get(position)
        holder.channelName.text = channel?.name
    }

    override fun getItemCount(): Int = channelList.value?.size?: 0
}