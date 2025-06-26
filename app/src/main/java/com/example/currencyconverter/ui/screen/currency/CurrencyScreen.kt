package com.example.currencyconverter.ui.screen.currency


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.currencyconverter.data.dataSource.remote.dto.RateDto
import com.example.currencyconverter.domain.entity.ExchangeRate
import com.example.currencyconverter.ui.navigation.LocalNavController
import com.example.currencyconverter.ui.screen.currency.component.RateItem


@Composable
fun CurrencyScreen(
    currencyViewModel: CurrencyVM = hiltViewModel(),
) {
    val navController = LocalNavController.current
    val rates by currencyViewModel.rates.collectAsState()
    val maneCurrency by currencyViewModel.maneCurrency.collectAsState()
    val maneAmount by currencyViewModel.maneAmount.collectAsState()
    val listState = rememberLazyListState()


    when {
        rates.isEmpty() -> CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        else -> {
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)) {
                LazyColumn(state = listState) {
                    items(items = rates, key = { it.secondaryCurrency.code }) { rate ->
                            RateItem(
                                rate = rate,
                                baseCurrency = maneCurrency,
                                onClick = {
                                    if (maneAmount == "1") {
                                        // Если в одинарном режиме — меняем базовую валюту
                                        currencyViewModel.setNewManeCurrency(rate.secondaryCurrency.code)
                                    } else {
                                        navController.navigate("exchange_screen/${maneCurrency}/${rate.secondaryCurrency.code}/$maneAmount/${rate.secondaryValue}")
                                    }
                                },
                                onAmountChange = { newAmount ->
                                    currencyViewModel.setAmount(newAmount)
                                },
                                amount = maneAmount,
                            )
                        }
                    }
                }
            }
        }
    }



