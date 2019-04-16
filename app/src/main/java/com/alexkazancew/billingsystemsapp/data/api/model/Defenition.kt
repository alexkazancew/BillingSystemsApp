package com.alexkazancew.billingsystemsapp.data.api.model

import com.google.gson.annotations.SerializedName

data class Defenition(
        @SerializedName("text")
        var text: String,
        @SerializedName("tr")
        var translation: List<Translation>

)