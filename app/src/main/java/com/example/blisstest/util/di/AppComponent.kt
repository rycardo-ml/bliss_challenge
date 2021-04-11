package com.example.blisstest.util.di

import com.example.blisstest.util.data.repository.GoogleReposRepository
import com.example.blisstest.util.data.repository.MainRepository
import com.example.blisstest.util.data.repository.UserRepository
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component( modules = [AppModule::class])
interface AppComponent {

    fun inject(repository: MainRepository)

    fun inject(repository: UserRepository)

    fun inject(repository: GoogleReposRepository)
}
