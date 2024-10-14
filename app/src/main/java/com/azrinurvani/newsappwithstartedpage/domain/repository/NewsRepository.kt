package com.azrinurvani.newsappwithstartedpage.domain.repository

import androidx.paging.PagingData
import com.azrinurvani.newsappwithstartedpage.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    fun getNews(sources : List<String>) : Flow<PagingData<Article>>
}