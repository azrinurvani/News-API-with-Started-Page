package com.azrinurvani.newsappwithstartedpage.domain.usecases.news

import androidx.paging.PagingData
import com.azrinurvani.newsappwithstartedpage.domain.model.Article
import com.azrinurvani.newsappwithstartedpage.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow


class GetNews(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(sources : List<String>) : Flow<PagingData<Article>> {
        return newsRepository.getNews(sources = sources)
    }
}