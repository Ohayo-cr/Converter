package com.example.currencyconverter.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.currencyconverter.ui.navigation.NavHostScreen


@Composable
fun MainScreen(navController: NavHostController) {

    Box(modifier = Modifier.fillMaxSize()) {

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            NavHostScreen(navController = navController)
        }
    }
}