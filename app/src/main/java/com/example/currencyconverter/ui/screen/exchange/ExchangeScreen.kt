package com.example.currencyconverter.ui.screen.exchange

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import com.example.currencyconverter.domain.entity.Currency
import com.example.currencyconverter.ui.screen.exchange.component.ExchangeItem
import com.example.currencyconverter.ui.screen.exchange.component.calculateExchangeRate
import com.example.currencyconverter.ui.utils.rounderNumber


@Composable
fun ExchangeScreen(fromCurrency: String, toCurrency: String, amount: String, value : String,
    viewModel: ExchangeVM = hiltViewModel()
    ) {
    val rates by viewModel.rates.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val course = if ((!isLoading) &&rates.size >= 2) {
        val first = rates[0]
        val second = rates[1]
        second.secondaryValue /  first.secondaryValue
    }  else {
        value.toDouble() / amount.toDouble()
    }
    val accountList by viewModel.accounts.collectAsState()
    val balanceMap = remember(accountList) {
        accountList.associateBy({ it.code }, { it.amount })
    }

    val exchangeRate = remember(fromCurrency, toCurrency, amount, value, balanceMap) {
        calculateExchangeRate(
            fromCurrencyCode = fromCurrency,
            toCurrencyCode = toCurrency,
            amount = amount,
            value = value,
            accountBalances = balanceMap
        )
    }

    LaunchedEffect(Unit) {
        viewModel.exchangeParams = mutableStateOf(
            ExchangeVM.ExchangeParams(fromCurrency, toCurrency, amount)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "${Currency.entries.find { it.code == toCurrency }?.fullName} " +
                    "to ${Currency.entries.find { it.code == fromCurrency }?.fullName}"
        )
        Text(text = "${Currency.entries.find { it.code == fromCurrency }?.symbol}1 = ${Currency.entries.find { it.code == toCurrency }?.symbol}${course.rounderNumber()} ")
        Spacer(modifier = Modifier.height(24.dp))

        if (!isLoading) {
            rates.forEach { rate ->
                ExchangeItem(
                    rate = rate,
                    baseCurrency = fromCurrency,
                    amount = if (rate.secondaryCurrency.code == fromCurrency) amount else value
                )
            }

        } else {
            exchangeRate.forEach { rate ->
                ExchangeItem(
                    rate = rate,
                    baseCurrency = fromCurrency,
                    amount = if (rate.secondaryCurrency.code == fromCurrency) amount else value
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {
                if (!isLoading) {
                }
            },
            enabled = !isLoading,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            if (isLoading) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        strokeWidth = 2.dp,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Loading course...")
                }
            } else {
                Text(text = "Bay ${Currency.entries.find { it.code == fromCurrency }?.fullName}  " +
                        "for ${Currency.entries.find { it.code == toCurrency }?.fullName}")
            }
        }
    }
}


