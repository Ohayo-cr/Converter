package com.example.currencyconverter.ui.screen.exchange

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.currencyconverter.ui.screen.currency.CurrencyVM
import com.example.currencyconverter.ui.screen.exchange.component.ExchangeItem
import com.example.currencyconverter.ui.utils.rounderNumber


@Composable
fun ExchangeScreen(
    currencyViewModel: CurrencyVM = hiltViewModel(),
    fromCurrencyCode: String,
    toCurrencyCode: String
) {
    val rates by currencyViewModel.rates.collectAsState()
    val amount by currencyViewModel.amount.collectAsState()

    // Получаем нужные курсы
    val fromRate = rates[fromCurrencyCode]
    val toRate = rates[toCurrencyCode]

    // Если нет данных — показываем прогресс
    if (fromRate == null || toRate == null) {
        CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        return
    }

    // Вычисляем курс обмена
    val exchangeRate = if (fromRate.rate > 0 && toRate.rate > 0) {
        toRate.rate / fromRate.rate
    } else {
        0.0
    }

    // Конвертируем сумму
    val convertedAmount = remember(amount, exchangeRate) {
        try {
            amount.toDoubleOrNull() ?: 0.0
        } catch (e: Exception) {
            0.0
        }
    } * exchangeRate

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
ExchangeItem()
        Spacer(modifier = Modifier.height(24.dp))

        // Кнопка обмена
        Button(
            onClick = {
                // Обмениваем валюты

            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Обменять")
        }
    }
}


