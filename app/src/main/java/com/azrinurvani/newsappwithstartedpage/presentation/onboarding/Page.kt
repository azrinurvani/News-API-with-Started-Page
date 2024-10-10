package com.azrinurvani.newsappwithstartedpage.presentation.onboarding

import androidx.annotation.DrawableRes
import com.azrinurvani.newsappwithstartedpage.R

data class Page(
    val title : String,
    val description: String,
    @DrawableRes val image : Int
)

val pages = listOf(
    Page(
        title = "Lorem ipsum is simply dummy",
        description = "Lorem ipsum is simply dummy text of the printing and typesetting industry.",
        image = R.drawable.onboarding1
    ),
    Page(
        title = "Lorem ipsum is simply dummy",
        description = "Lorem ipsum is simply dummy text of the printing and typesetting industry.",
        image = R.drawable.onboarding2
    ),
    Page(
        title = "Lorem ipsum is simply dummy",
        description = "Lorem ipsum is simply dummy text of the printing and typesetting industry.",
        image = R.drawable.onboarding3
    ),
)