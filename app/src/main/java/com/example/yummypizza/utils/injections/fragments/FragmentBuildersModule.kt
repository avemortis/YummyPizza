package com.example.yummypizza.utils.injections.fragments

import com.example.yummypizza.ui.screens.preview.PreviewFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributePreviewFragment(): PreviewFragment
}