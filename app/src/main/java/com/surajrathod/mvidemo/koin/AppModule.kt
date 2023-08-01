package com.surajrathod.mvidemo.koin

import com.surajrathod.mvidemo.network.NetworkInterface
import com.surajrathod.mvidemo.repository.MainRepoImpl
import com.surajrathod.mvidemo.repository.MainRepository
import com.surajrathod.mvidemo.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single {
        Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(NetworkInterface::class.java)
    }
    single<MainRepository> {
        MainRepoImpl(get())
    }
    viewModel{
        MainViewModel(get())
    }
}