package com.example.blisstest.util.di

import com.example.blisstest.util.data.repository.MainRepository
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component( modules = [AppModule::class])
interface AppComponent {

    fun inject(repository: MainRepository)

}