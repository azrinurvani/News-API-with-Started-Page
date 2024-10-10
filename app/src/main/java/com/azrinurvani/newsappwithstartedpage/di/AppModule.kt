package com.azrinurvani.newsappwithstartedpage.di

import android.app.Application
import com.azrinurvani.newsappwithstartedpage.data.manager.LocalUserManagerImpl
import com.azrinurvani.newsappwithstartedpage.domain.manager.LocalUserManager
import com.azrinurvani.newsappwithstartedpage.domain.usecases.AppEntryUseCases
import com.azrinurvani.newsappwithstartedpage.domain.usecases.ReadAppEntry
import com.azrinurvani.newsappwithstartedpage.domain.usecases.SaveAppEntry
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLocalUserManager(application: Application) :LocalUserManager{
        return LocalUserManagerImpl(application)
    }

    @Provides
    @Singleton
    fun provideAppEntryUseCases(localUserManager: LocalUserManager) : AppEntryUseCases{
        return AppEntryUseCases(
            readAppEntry = ReadAppEntry(localUserManager = localUserManager),
            saveAppEntry = SaveAppEntry(localUserManager = localUserManager)
        )
    }

}