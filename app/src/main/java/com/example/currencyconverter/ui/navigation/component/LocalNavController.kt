package com.example.currencyconverter.ui.navigation.component

import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController

val LocalNavController = compositionLocalOf<NavHostController> {
    error("No NavHostController provided")
}