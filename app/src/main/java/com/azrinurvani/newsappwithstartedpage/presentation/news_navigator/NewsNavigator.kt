package com.azrinurvani.newsappwithstartedpage.presentation.news_navigator

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.azrinurvani.newsappwithstartedpage.R
import com.azrinurvani.newsappwithstartedpage.domain.model.Article
import com.azrinurvani.newsappwithstartedpage.presentation.bookmark.BookmarkScreen
import com.azrinurvani.newsappwithstartedpage.presentation.bookmark.BookmarkViewModel
import com.azrinurvani.newsappwithstartedpage.presentation.details.DetailsEvent
import com.azrinurvani.newsappwithstartedpage.presentation.details.DetailsScreen
import com.azrinurvani.newsappwithstartedpage.presentation.details.DetailsViewModel
import com.azrinurvani.newsappwithstartedpage.presentation.home.HomeScreen
import com.azrinurvani.newsappwithstartedpage.presentation.home.HomeViewModel
import com.azrinurvani.newsappwithstartedpage.presentation.nav_graph.Route
import com.azrinurvani.newsappwithstartedpage.presentation.search.SearchScreen
import com.azrinurvani.newsappwithstartedpage.presentation.search.SearchViewModel

@Composable
fun NewsNavigator(){

    val bottomNavigationItem = remember {
        listOf(
            BottomNavigationItem(
                icon = R.drawable.ic_home, text = "Home"
            ),
            BottomNavigationItem(
                icon = R.drawable.ic_search, text = "Search"
            ),
            BottomNavigationItem(
                icon = R.drawable.ic_bookmark, text = "Bookmark"
            )
        )
    }

    val navController = rememberNavController()
    val backStackState = navController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable {
        mutableIntStateOf(0)
    }

    selectedItem = remember(key1 = backStackState){
        when(backStackState?.destination?.route){
            Route.HomeScreen.route -> 0
            Route.SearchScreen.route -> 1
            Route.BookmarkScreen.route -> 2
            else -> 0
        }
    }

    var isBottomBarVisible = remember(key1 = backStackState){
        backStackState?.destination?.route == Route.HomeScreen.route ||
                backStackState?.destination?.route == Route.SearchScreen.route ||
                backStackState?.destination?.route == Route.BookmarkScreen.route
    }

    Scaffold (
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (isBottomBarVisible){
                NewsBottomNavigation(
                    items = bottomNavigationItem,
                    selected = selectedItem,
                    onItemClick = { index ->
                        when(index){
                            0 -> navigateToTab(
                                navController = navController,
                                route = Route.HomeScreen.route
                            )
                            1 -> navigateToTab(
                                navController = navController,
                                route = Route.SearchScreen.route
                            )
                            2 -> navigateToTab(
                                navController = navController,
                                route = Route.BookmarkScreen.route
                            )

                        }
                    }
                )
            }
        }

    ){ innerPadding ->
        val bottomPadding = innerPadding.calculateBottomPadding()
        NavHost(
            navController = navController,
            startDestination = Route.HomeScreen.route,
            modifier = Modifier.padding(bottom = bottomPadding)
        ){
            composable(route = Route.HomeScreen.route) {
                val viewModel : HomeViewModel = hiltViewModel()
                val articles = viewModel.news.collectAsLazyPagingItems()
                HomeScreen(
                    articles = articles,
                    navigateToSearch = {
                        navigateToTab(
                            navController = navController,
                            route = Route.SearchScreen.route
                        )
                    },
                    navigateToDetails = { article ->
                        navigateToDetails(
                            navController = navController,
                            article = article
                        )
                    }
                )
            }
            composable(route = Route.SearchScreen.route) {
                val viewModel : SearchViewModel = hiltViewModel()
                val state = viewModel.state.value
                SearchScreen(
                    state = state,
                    event = viewModel::onEvent,
                    navigateToDetails = { article->
                        navigateToDetails(
                            navController = navController,
                            article = article
                        )
                    }
                )
            }
            composable(route = Route.DetailsScreen.route) {
                val viewModel : DetailsViewModel = hiltViewModel()

                //TODO : Handle Side Effect
                if (viewModel.sideEffect!= null){
                    Toast.makeText(LocalContext.current,viewModel.sideEffect,Toast.LENGTH_LONG).show()
                    viewModel.onEvent(DetailsEvent.RemoveSideEffect)
                }

                navController.previousBackStackEntry?.savedStateHandle?.get<Article>("article")?.let { article->
                    DetailsScreen(
                        article = article,
                        event = viewModel::onEvent,
                        navigateUp = {
                            navController.navigateUp()
                        }
                    )
                }
            }
            composable(route = Route.BookmarkScreen.route) {
                val viewModel : BookmarkViewModel = hiltViewModel()
                val state = viewModel.state.value
                BookmarkScreen(
                    state = state,
                    navigateToDetails = { article->
                        navigateToDetails(
                            navController = navController,
                            article = article
                        )
                    }
                )
            }
        }
    }
}

private fun navigateToTab(navController: NavController, route : String){
    navController.navigate(route){
        navController.graph.startDestinationRoute?.let { homeScreen->
            popUpTo(homeScreen){
                saveState = true
            }
            restoreState = true
            launchSingleTop = true
        }
    }
}

fun navigateToDetails(navController: NavController, article: Article){
    navController.currentBackStackEntry?.savedStateHandle?.set("article",article) //to handle pass an object with not data primitive like string, int, boolean, etc
    navController.navigate(
        route = Route.DetailsScreen.route
    )
}