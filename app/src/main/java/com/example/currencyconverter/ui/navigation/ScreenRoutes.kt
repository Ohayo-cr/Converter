package com.example.currencyconverter.ui.navigation

sealed class Screen(val route: String) {

    object Currencies : Screen("currencies")
    object Exchange : Screen("exchange")
    object Transactions : Screen("transactions")
}