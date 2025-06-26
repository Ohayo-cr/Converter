package com.example.currencyconverter.ui.screen.exchange

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.currencyconverter.ui.screen.exchange.component.ExchangeItem


@Composable
fun ExchangeScreen(fromCurrency: String, toCurrency: String, amount: String, value : String,
    viewModel: ExchangeVM = hiltViewModel()
    ) {

    val rates by viewModel.rates.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.exchangeParams = mutableStateOf(
            ExchangeVM.ExchangeParams(fromCurrency, toCurrency, amount)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(24.dp))



        // Отображаем ExchangeItem для каждой выбранной валюты
        rates.forEach { rate ->
            ExchangeItem(
                rate = rate,
                baseCurrency = fromCurrency,
                onAmountChange = { newAmount ->   viewModel.updateAmount(newAmount) },
                amount = if (rate.currency.code == fromCurrency) amount else ""
            )


        }
        // Кнопка обмена
        Button(
            onClick = {
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Обменять")
        }
        Text(value)
        Text(fromCurrency)
        Text(toCurrency)
        Text(value)


    }
}


