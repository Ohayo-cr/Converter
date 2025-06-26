package com.example.currencyconverter.ui.navigation

sealed class Screen(val route: String) {
    object Currency : Screen("currency_screen")
    object Exchange : Screen("exchange_screen") {
        const val routeWithArgs = "exchange_screen/{base}/{target}/{amount}/{value}"

    }
    object Transactions : Screen("transactions_screen")
}