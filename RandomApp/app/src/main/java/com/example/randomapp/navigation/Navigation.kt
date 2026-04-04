package com.example.randomapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.randomapp.ui.screens.HomeScreen
import com.example.randomapp.ui.screens.LoginScreen
import com.example.randomapp.ui.screens.ProfileScreen
import com.example.randomapp.ui.screens.RandomFoodScreen
import com.example.randomapp.ui.screens.RandomNameScreen
import com.example.randomapp.ui.screens.RandomNumberScreen
import com.example.randomapp.ui.screens.SplashScreen

@Composable
fun PicklyNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH
    ) {
        composable(Routes.SPLASH) {
            SplashScreen(navController = navController)
        }
        composable(Routes.LOGIN) {
            LoginScreen(navController = navController)
        }
        composable(Routes.HOME) {
            HomeScreen(navController = navController)
        }
        composable(Routes.RANDOM_NAME) {
            RandomNameScreen(navController = navController)
        }
        composable(Routes.RANDOM_NUMBER) {
            RandomNumberScreen(navController = navController)
        }
        composable(Routes.RANDOM_FOOD) {
            RandomFoodScreen(navController = navController)
        }
        composable(Routes.PROFILE) {
            ProfileScreen(navController = navController)
        }
    }
}
