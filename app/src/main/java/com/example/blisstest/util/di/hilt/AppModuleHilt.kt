package com.example.blisstest.util.di.hilt


import android.content.Context
import com.example.blisstest.presentation.BlissApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModuleHilt {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): BlissApplication {
        return app as BlissApplication
    }
}