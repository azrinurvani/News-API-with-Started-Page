package com.azrinurvani.newsappwithstartedpage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.azrinurvani.newsappwithstartedpage.data.local.NewsDao
import com.azrinurvani.newsappwithstartedpage.domain.model.Article
import com.azrinurvani.newsappwithstartedpage.domain.model.Source
import com.azrinurvani.newsappwithstartedpage.presentation.nav_graph.NavGraph
import com.azrinurvani.newsappwithstartedpage.ui.theme.NewsAppWithStartedPageTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val viewModel by viewModels<MainViewModel>()

    @Inject
    lateinit var newsDao: NewsDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window,false)

        lifecycleScope.launch {
            newsDao.upsert(
                Article(
                    author = "",
                    content = "",
                    description = "",
                    publishedAt = "2 hours",
                    source = Source(id = "", name = "BBC"),
                    title = "Her train broke down. Her phone died. And then she met her Saver in a",
                    url = "",
                    urlToImage = "https://ichef.bbci.co.uk/live-experience/cps/624/cpsprodpb/11787/production/_124395517_bbcbreakingnewsgraphic.jpg"
                ),
            )
        }
        installSplashScreen().apply {
            //keep splash screen still visible until fetch the start destination
            //from data store preferences
            setKeepOnScreenCondition{
                viewModel.splashCondition
            }
        }

        setContent {
            NewsAppWithStartedPageTheme {
                val isSystemInDarkMode = isSystemInDarkTheme()
                val systemController = rememberSystemUiController()


                SideEffect {
                    systemController.setSystemBarsColor(
                        color = Color.Transparent,
                        darkIcons = !isSystemInDarkMode
                    )
                }

                Box(
                    modifier = Modifier.background(MaterialTheme.colorScheme.background)
                ) {
                    val startDestination = viewModel.startDestination
                    NavGraph(
                        startDestination = startDestination
                    )
                }
            }
        }
    }
}