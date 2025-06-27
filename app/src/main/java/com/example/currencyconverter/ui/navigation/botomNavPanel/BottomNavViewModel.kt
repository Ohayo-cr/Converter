package com.example.currencyconverter.ui.navigation.botomNavPanel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.currencyconverter.ui.navigation.botomNavPanel.component.VisiblePanel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class BottomNavViewModel @Inject constructor() : ViewModel() {



    private val _selectedItem = mutableStateOf("")
    val selectedItem: State<String> get() = _selectedItem

    fun selectItem(route: String) {
        if (route in VisiblePanel.routesToShowBottomNav) {
            _selectedItem.value = route
        }
    }


    private val routesToShowBottomNav by lazy { VisiblePanel.routesToShowBottomNav.toSet() }

    private val _currentRoute = MutableStateFlow<String?>(null)
    val currentRoute: StateFlow<String?> get() = _currentRoute

    private val _shouldShowBottomNavigation = MutableStateFlow(false)
    val shouldShowBottomNavigation: StateFlow<Boolean> get() = _shouldShowBottomNavigation

    fun updateNavigation(route: String?) {
        _currentRoute.value = route
        _shouldShowBottomNavigation.value = route != null && route in routesToShowBottomNav
    }

}