package com.azrinurvani.newsappwithstartedpage.domain.usecases.news

import com.azrinurvani.newsappwithstartedpage.domain.model.Article
import com.azrinurvani.newsappwithstartedpage.domain.repository.NewsRepository

class DeleteArticle(
    private val newsRepository: NewsRepository
) {

    suspend operator fun invoke(article: Article) {
        newsRepository.deleteArticle(article = article)
    }
}