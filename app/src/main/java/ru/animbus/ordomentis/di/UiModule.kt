package ru.animbus.ordomentis.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.animbus.ordomentis.ui.items.ItemsViewModel
import ru.animbus.ordomentis.ui.main.MainViewModel
import ru.animbus.ordomentis.ui.units.UnitsViewModel
import ru.animbus.ordomentis.ui.users.UsersViewModel

val uiModule = module {

    viewModel { MainViewModel(get()) }
    viewModel { ItemsViewModel(get()) }
    viewModel { UnitsViewModel(get()) }
    viewModel { UsersViewModel(get()) }
}