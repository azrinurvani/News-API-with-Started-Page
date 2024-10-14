package com.azrinurvani.newsappwithstartedpage.presentation.bookmark

import com.azrinurvani.newsappwithstartedpage.domain.model.Article

data class BookmarkState(
    val articles : List<Article> = emptyList()
)
