package com.azrinurvani.newsappwithstartedpage.data.remote.dto

import com.azrinurvani.newsappwithstartedpage.domain.model.Article

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)