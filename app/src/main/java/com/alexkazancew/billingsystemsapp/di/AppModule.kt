package com.alexkazancew.billingsystemsapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.alexkazancew.billingsystemsapp.BillingApp
import com.alexkazancew.billingsystemsapp.BuildConfig
import com.alexkazancew.billingsystemsapp.data.api.DictionaryApiService
import com.alexkazancew.billingsystemsapp.data.cache.TranslationApiCache
import com.alexkazancew.billingsystemsapp.data.db.WordsDAO
import com.alexkazancew.billingsystemsapp.data.db.WordsDatabase
import com.alexkazancew.billingsystemsapp.data.mappers.Response2HintsConverter
import com.alexkazancew.billingsystemsapp.data.repositories.HintsRepository
import com.alexkazancew.billingsystemsapp.data.repositories.WordsRepository
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideWordDataSource(app: Application): WordsDAO {
        return Room.databaseBuilder(app,
                WordsDatabase::class.java, "words.db")
                .fallbackToDestructiveMigration()
                .build().wordsDao()
    }

    @Singleton
    @Provides
    fun provideTranslationResponseCache() = TranslationApiCache()


    @Singleton
    @Provides
    fun provideOkHttp(): OkHttpClient {
        var builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            var interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(interceptor)
        }
        return builder.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl("https://dictionary.yandex.net/api/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    @Singleton
    @Provides
    fun provideDictionaryService(retrofit: Retrofit): DictionaryApiService {
        return retrofit.create(DictionaryApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideResponse2HintsConverter() = Response2HintsConverter()

    @Singleton
    @Provides
    fun provideHintsRepository(cache: TranslationApiCache, api: DictionaryApiService, converter: Response2HintsConverter): HintsRepository {
        return HintsRepository(cache, api, converter)
    }

    @Singleton
    @Provides
    fun provideWordsRepository(wordsDAO: WordsDAO): WordsRepository {
        return WordsRepository(wordsDAO)
    }
}