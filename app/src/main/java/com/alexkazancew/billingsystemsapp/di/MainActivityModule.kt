package com.alexkazancew.billingsystemsapp.di

import com.alexkazancew.billingsystemsapp.MainActivity
import com.alexkazancew.billingsystemsapp.di.FragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class MainActivityModule {
    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun contributeMainActivity(): MainActivity
}