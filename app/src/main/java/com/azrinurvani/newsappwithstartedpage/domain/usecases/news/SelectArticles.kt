package com.azrinurvani.newsappwithstartedpage.domain.usecases.news

import com.azrinurvani.newsappwithstartedpage.data.local.NewsDao
import com.azrinurvani.newsappwithstartedpage.domain.model.Article
import kotlinx.coroutines.flow.Flow

class SelectArticles(
    private val newsDao : NewsDao
) {
    operator fun invoke() : Flow<List<Article>>{
       return newsDao.getArticles()
    }
}