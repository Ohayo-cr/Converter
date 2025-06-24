package com.example.currencyconverter.ui.navigation

sealed class Screen(val route: String) {

    object Currency : Screen("currency")
    object Exchange : Screen("exchange")
    object Transactions : Screen("transactions")
}