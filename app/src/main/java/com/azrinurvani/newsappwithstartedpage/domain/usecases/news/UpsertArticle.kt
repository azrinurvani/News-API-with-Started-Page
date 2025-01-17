package com.azrinurvani.newsappwithstartedpage.domain.usecases.news

import com.azrinurvani.newsappwithstartedpage.domain.model.Article
import com.azrinurvani.newsappwithstartedpage.domain.repository.NewsRepository

class UpsertArticle(
    private val newsRepository: NewsRepository
) {

    suspend operator fun invoke(article: Article) {
        newsRepository.upsertArticle(article = article)
    }
}