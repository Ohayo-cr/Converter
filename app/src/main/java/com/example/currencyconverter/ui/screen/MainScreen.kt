package com.example.currencyconverter.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.currencyconverter.ui.navigation.NavHostScreen
import com.example.currencyconverter.ui.navigation.botomNavPanel.BottomNavViewModel
import com.example.currencyconverter.ui.navigation.botomNavPanel.BottomPanel


@Composable
fun MainScreen(navController: NavHostController,viewModel: BottomNavViewModel = hiltViewModel()) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    LaunchedEffect(currentRoute) {
        viewModel.updateNavigation(currentRoute)
    }


    val showBottomNav by viewModel.shouldShowBottomNavigation.collectAsState()


    val bottomBarHeight = 64.dp

    Box(modifier = Modifier.fillMaxSize()) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = if (showBottomNav) bottomBarHeight else 0.dp),
        ) {
            NavHostScreen(navController = navController)
        }
        if (showBottomNav) {
            Box(
                modifier = Modifier.align(Alignment.BottomCenter),
                contentAlignment = Alignment.Center
            ) {
                BottomPanel(navController = navController, viewModel = viewModel)
            }
        }
    }
    }
