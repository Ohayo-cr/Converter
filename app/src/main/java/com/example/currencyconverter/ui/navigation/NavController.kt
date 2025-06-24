package com.example.currencyconverter.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable


import com.example.currencyconverter.ui.screen.currency.CurrencyScreen

@Composable
fun NavHostScreen(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Currency.route,
        enterTransition = { fadeIn(animationSpec = tween(0)) },
        exitTransition = { fadeOut(animationSpec = tween(0)) },
        popEnterTransition = { fadeIn(animationSpec = tween(0)) },
        popExitTransition = { fadeOut(animationSpec = tween(0)) }
    ) {
        composable(Screen.Currency.route) {
            CurrencyScreen()
        }

    }
}