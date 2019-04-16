package com.alexkazancew.billingsystemsapp.data.api

import com.alexkazancew.billingsystemsapp.data.api.model.TranslateResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query


private val API_KEY = "dict.1.1.20190415T063153Z.4accfee081f1aca9.943a62c49b1ecf011dd4b25ee094e1866dfe3d72"

interface DictionaryApiService {

    @GET("v1/dicservice.json/lookup")
    fun translateText(@Query("key") apiKey: String = API_KEY,
                      @Query("lang") lang: String,
                      @Query("text") text: String): Flowable<TranslateResponse>
}