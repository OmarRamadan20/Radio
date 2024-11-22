package com.example.radio

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.radio.adapter.ChannelAdapter
import com.example.radio.databinding.FragmentRadioBinding
import android.view.ViewGroup as ViewGroup1

class RadioFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var channelAdapter: ChannelAdapter

    var mediaPlayer:MediaPlayer?=null
    lateinit var binding : FragmentRadioBinding
    lateinit var viewModel: RadioViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup1?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRadioBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[RadioViewModel::class.java]
        creationViews()
        return binding.root
    }

    private fun creationViews() {
        recyclerView = binding.recyclerView
        viewModel.loadRadios()
        channelAdapter = ChannelAdapter(viewModel.radios)
        recyclerView.adapter = channelAdapter
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DataBindingUtil.bind(view)!!
        initBindings()
        observeData()
        service()
    }

    private fun service() {
        val intent = Intent(requireActivity(),MyService::class.java)
        ContextCompat.startForegroundService(requireContext(),intent)
    }


    private fun initBindings() {
        binding.vm=viewModel
        binding.lifecycleOwner=viewLifecycleOwner
    }

    private fun observeData() {
        viewModel.toastMessage.observe(viewLifecycleOwner) { message ->
            message?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val intent = Intent(requireActivity(), MyService::class.java)
        requireActivity().stopService(intent)

        if(mediaPlayer!!.isPlaying){
            mediaPlayer!!.stop()
        }
    }


}