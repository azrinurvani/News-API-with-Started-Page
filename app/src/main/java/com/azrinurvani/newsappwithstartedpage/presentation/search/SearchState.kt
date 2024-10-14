package com.azrinurvani.newsappwithstartedpage.presentation.search

import androidx.paging.PagingData
import com.azrinurvani.newsappwithstartedpage.domain.model.Article
import kotlinx.coroutines.flow.Flow

data class SearchState(
    val searchQuery : String = "",
    val articles : Flow<PagingData<Article>>? = null
)