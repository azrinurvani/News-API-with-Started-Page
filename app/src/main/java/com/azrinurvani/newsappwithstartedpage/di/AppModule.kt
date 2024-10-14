package com.azrinurvani.newsappwithstartedpage.di

import android.app.Application
import android.util.Log
import com.azrinurvani.newsappwithstartedpage.data.manager.LocalUserManagerImpl
import com.azrinurvani.newsappwithstartedpage.data.remote.NewsApi
import com.azrinurvani.newsappwithstartedpage.data.repository.NewsRepositoryImpl
import com.azrinurvani.newsappwithstartedpage.domain.manager.LocalUserManager
import com.azrinurvani.newsappwithstartedpage.domain.repository.NewsRepository
import com.azrinurvani.newsappwithstartedpage.domain.usecases.app_entry.AppEntryUseCases
import com.azrinurvani.newsappwithstartedpage.domain.usecases.app_entry.ReadAppEntry
import com.azrinurvani.newsappwithstartedpage.domain.usecases.app_entry.SaveAppEntry
import com.azrinurvani.newsappwithstartedpage.domain.usecases.news.GetNews
import com.azrinurvani.newsappwithstartedpage.domain.usecases.news.NewsUseCases
import com.azrinurvani.newsappwithstartedpage.domain.usecases.news.SearchNews
import com.azrinurvani.newsappwithstartedpage.util.Constants.BASE_URL
import com.azrinurvani.newsappwithstartedpage.util.Constants.CALL_TIMEOUT
import com.azrinurvani.newsappwithstartedpage.util.Constants.CONNECT_TIMEOUT
import com.azrinurvani.newsappwithstartedpage.util.Constants.READ_TIMEOUT
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
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
    fun provideAppEntryUseCases(localUserManager: LocalUserManager) : AppEntryUseCases {
        return AppEntryUseCases(
            readAppEntry = ReadAppEntry(localUserManager = localUserManager),
            saveAppEntry = SaveAppEntry(localUserManager = localUserManager)
        )
    }

    @Provides
    fun provideOkHttpClient() : OkHttpClient {
        val interceptor = HttpLoggingInterceptor { message ->
            Log.d("News-API", "log: $message")
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(
                interceptor
            ).connectTimeout(
                timeout = CONNECT_TIMEOUT,
                TimeUnit.SECONDS
            )
            .readTimeout(
                timeout = READ_TIMEOUT,
                TimeUnit.SECONDS
            )
            .callTimeout(
                timeout = CALL_TIMEOUT,
                TimeUnit.SECONDS
            )
//            .interceptors()
//            .add(Interceptor { TODO("Not yet implemented") })
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsApi(httpClient: OkHttpClient) : NewsApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
            .create(NewsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(
        newsApi: NewsApi
    ) : NewsRepository{
        return NewsRepositoryImpl(newsApi)
    }

    @Provides
    @Singleton
    fun provideNewsUseCases(newsRepository: NewsRepository) : NewsUseCases{
        return NewsUseCases(
            getNews = GetNews(newsRepository = newsRepository),
            searchNews = SearchNews(newsRepository = newsRepository)
        )
    }

}