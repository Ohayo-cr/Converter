package com.example.currencyconverter.ui.screen.currency


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.currencyconverter.data.dataSource.remote.dto.RateDto
import com.example.currencyconverter.ui.utils.rounderNumber
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun CurrencyScreen(
    currencyViewModel: CurrencyVM = hiltViewModel()
) {
    val rates by currencyViewModel.rates.collectAsState()
    val baseCurrency by currencyViewModel.baseCurrency.collectAsState()
    val amount by currencyViewModel.amount.collectAsState()

    LaunchedEffect(Unit) {
        currencyViewModel.loadRates(
            baseCurrencyCode = baseCurrency,
            amount = amount.toDoubleOrNull() ?: 1.0
        )
    }


    when {
        rates.isEmpty() -> CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        else -> {
            Column(modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp)) {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(rates) { rate ->
                            RateItem(
                                rate = rate,
                                baseCurrency = baseCurrency,
                                onClick = {
                                    currencyViewModel.setBaseCurrency(rate.currency)
                                },
                                onValueClick = {}
                            )
                        }
                    }
                }
            }
        }
    }


@Composable
 fun RateItem(
    rate: RateDto,
    baseCurrency: String,
    onClick: () -> Unit,
    onValueClick: () -> Unit,
) {
    val isSelected = rate.currency == baseCurrency

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
            .clickable {
                onClick()
            },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = rate.currency,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            )
            Text(
                text = rate.value.rounderNumber(),
                modifier = Modifier
                    .clickable(enabled = isSelected) {
                        onValueClick()
                    }
                    .padding(horizontal = 4.dp)
            )
        }
    }
}

