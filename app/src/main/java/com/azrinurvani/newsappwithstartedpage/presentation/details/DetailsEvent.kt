package com.azrinurvani.newsappwithstartedpage.presentation.details

import com.azrinurvani.newsappwithstartedpage.domain.model.Article

sealed class DetailsEvent {

    data class UpsertDeleteArticle(val article:Article) : DetailsEvent()
    object RemoveSideEffect : DetailsEvent()
}