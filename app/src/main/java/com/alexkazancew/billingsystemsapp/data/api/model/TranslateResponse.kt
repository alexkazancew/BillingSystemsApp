package com.alexkazancew.billingsystemsapp.data.api.model

import com.google.gson.annotations.SerializedName

data class TranslateResponse(
        @SerializedName("def")
        var defenition: List<Defenition>
)