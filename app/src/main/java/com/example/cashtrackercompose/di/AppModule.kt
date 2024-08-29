package com.example.cashtrackercompose.di


import com.example.cashtrackercompose.entry.EntryViewModel
import com.example.cashtrackercompose.model.EntryData
import com.example.cashtrackercompose.repo.EntryDataImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    singleOf(::EntryDataImpl) {
        bind<EntryData>()
    }
    viewModel {
        EntryViewModel(get(), get())
    }
}