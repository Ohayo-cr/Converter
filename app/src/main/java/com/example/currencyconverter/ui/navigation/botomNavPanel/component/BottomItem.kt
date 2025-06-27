package com.example.currencyconverter.ui.navigation.botomNavPanel.component

import com.example.currencyconverter.R
import com.example.currencyconverter.ui.navigation.component.Screen

sealed class BottomItem(val title: String, val iconId: Int, val route: String) {
    object Screen1 : BottomItem("Transactions", R.drawable.ic_transaction, Screen.Transactions.route)
    object Screen2 : BottomItem("Trade", R.drawable.ic_trade, Screen.Currency.route)

}