package com.example.yummypizza.utils.injections.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.yummypizza.ui.screens.menu.MenuViewModel
import com.example.yummypizza.ui.screens.menu.item.MenuItemBottomSheetViewModel
import com.example.yummypizza.ui.screens.preview.PreviewViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(PreviewViewModel::class)
    internal abstract fun previewViewModel(viewModel: PreviewViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MenuItemBottomSheetViewModel::class)
    internal abstract fun menuBottomSheetViewModel(viewModel: MenuItemBottomSheetViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MenuViewModel::class)
    internal abstract fun menuViewModel(viewModel: MenuViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(
        factory: ViewModelFactory
    ): ViewModelProvider.Factory
}
