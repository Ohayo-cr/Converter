package com.example.currencyconverter.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.currencyconverter.ui.screen.currency.CurrencyScreen
import com.example.currencyconverter.ui.screen.exchange.ExchangeScreen


@Composable
fun NavHostScreen(navController: NavHostController) {

    CompositionLocalProvider(LocalNavController provides navController) {
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

            composable(
                Screen.Exchange.routeWithArgs,
                arguments = listOf(
                    navArgument("base") { type = NavType.StringType },
                    navArgument("target") { type = NavType.StringType },
                    navArgument("amount") { type = NavType.StringType },
                    navArgument("value") { type = NavType.StringType },
                )
            ) { backStackEntry ->
                val base = backStackEntry.arguments?.getString("base") ?: ""
                val target = backStackEntry.arguments?.getString("target") ?: ""
                val amount = backStackEntry.arguments?.getString("amount") ?: ""
                val value = backStackEntry.arguments?.getString("value") ?: ""

                ExchangeScreen(fromCurrency = base, toCurrency = target, amount = amount, value = value)
            }
        }
    }
}