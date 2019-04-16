package com.alexkazancew.billingsystemsapp.data.api.model

import com.google.gson.annotations.SerializedName

data class Translation(
        @SerializedName("text")
        var text: String
)