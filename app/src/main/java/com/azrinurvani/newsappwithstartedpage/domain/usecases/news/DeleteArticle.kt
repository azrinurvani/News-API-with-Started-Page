package com.azrinurvani.newsappwithstartedpage.domain.usecases.news

import com.azrinurvani.newsappwithstartedpage.data.local.NewsDao
import com.azrinurvani.newsappwithstartedpage.domain.model.Article

class DeleteArticle(
    private val newsDao : NewsDao
) {

    suspend operator fun invoke(article: Article) {
        newsDao.delete(article = article)
    }
}