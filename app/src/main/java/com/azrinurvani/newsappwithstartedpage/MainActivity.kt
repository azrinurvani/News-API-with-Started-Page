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
import com.azrinurvani.newsappwithstartedpage.presentation.nav_graph.NavGraph
import com.azrinurvani.newsappwithstartedpage.ui.theme.NewsAppWithStartedPageTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window,false)
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