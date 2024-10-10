package com.azrinurvani.newsappwithstartedpage.presentation.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.azrinurvani.newsappwithstartedpage.presentation.Dimens.MediumPadding2
import com.azrinurvani.newsappwithstartedpage.presentation.Dimens.PageIndicatorWidth
import com.azrinurvani.newsappwithstartedpage.presentation.common.NewsButton
import com.azrinurvani.newsappwithstartedpage.presentation.common.NewsTextButton
import com.azrinurvani.newsappwithstartedpage.presentation.onboarding.component.OnBoardingPage
import com.azrinurvani.newsappwithstartedpage.presentation.onboarding.component.PageIndicator
import kotlinx.coroutines.launch


@Composable
fun OnBoardingScreen(){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        val pagerState = rememberPagerState(
            initialPage = 0
        ) {
            pages.size
        }
        val buttonState = remember{
            derivedStateOf {
                when (pagerState.currentPage){
                    0 -> listOf("","Next")
                    1 -> listOf("Back","Next")
                    2 -> listOf("Back","Get Started")
                    else -> listOf("","")
                }
            }
        }

        HorizontalPager(
            state = pagerState
        ) { index ->
            OnBoardingPage(
                page = pages[index]
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MediumPadding2)
                .navigationBarsPadding(), //get padding from navigation bars in android like swipe to close or minimize app
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            PageIndicator(
                modifier = Modifier.width(PageIndicatorWidth),
                pageSize = pages.size,
                selectedPage = pagerState.currentPage
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                val coroutineScope = rememberCoroutineScope()
                if (buttonState.value[0].isNotEmpty()){
                    NewsTextButton(
                        text = buttonState.value[0],
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(
                                    page = pagerState.currentPage - 1 //back to page before
                                )
                            }
                        }
                    )
                }
                NewsButton(
                    text = buttonState.value[1],
                    onClick = {
                        coroutineScope.launch {
                            if (pagerState.currentPage == 3) {
                                //navigate to Home Screen
                            }else{
                                pagerState.animateScrollToPage(
                                    page = pagerState.currentPage + 1 //next page
                                )
                            }
                        }
                    }
                )
            }
        }
        Spacer(
            modifier = Modifier.weight(0.5f)
        )
    }

}