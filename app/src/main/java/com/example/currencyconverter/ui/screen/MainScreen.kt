package com.example.currencyconverter.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.currencyconverter.ui.navigation.NavHostScreen


@Composable
fun MainScreen(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        // Основной контент
        Box(
            modifier = Modifier
                .fillMaxSize()

        ) {
            NavHostScreen(navController = navController)

        }
    }
}