package com.example.radio.api.model

import com.google.gson.annotations.SerializedName

data class IzaaResponse(

	@field:SerializedName("radios")
	val radios: List<RadiosItem?>? = null
)