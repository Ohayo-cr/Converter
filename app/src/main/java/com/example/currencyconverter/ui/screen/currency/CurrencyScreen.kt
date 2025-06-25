package com.example.currencyconverter.ui.screen.currency


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.currencyconverter.data.dataSource.remote.dto.RateDto
import com.example.currencyconverter.domain.entity.ExchangeRate
import com.example.currencyconverter.ui.utils.rounderNumber


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
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)) {
                LazyColumn {
                    items(items = rates, key = { it.currency.code }) { rate ->
                            RateItem(
                                rate = rate,
                                baseCurrency = baseCurrency,
                                onClick = {
                                    currencyViewModel.setBaseCurrency(rate.currency.code)
                                },
                                onValueClick = {},
                                onAmountChange = { newAmount ->
                                    currencyViewModel.setAmount(newAmount)
                                },
                                amount = amount
                            )
                        }
                    }
                }
            }
        }
    }


@Composable
fun RateItem(
    rate: ExchangeRate,
    baseCurrency: String,
    onClick: () -> Unit,
    onValueClick: () -> Unit,
    onAmountChange: (String) -> Unit,
    amount: String
) {
    val isSelected = rate.currency.code == baseCurrency
    val painter = painterResource(id = rate.currency.flagResId)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
            .clickable {
                onClick()
            }
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)

            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {

                Text(
                    text = rate.currency.code,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                )
                Text(
                    text = rate.currency.fullName,
                    fontWeight = FontWeight.Normal,
                    color = Color.DarkGray.copy(alpha = .6f)
                )
                rate.balance?.let {
                Text(
                    text = "Balance: ${rate.currency.symbol} ${rate.balance.rounderNumber()}",
                    fontWeight = FontWeight.Normal,
                    color = Color.DarkGray.copy(alpha = .6f)
                )
            }
            }

            Row(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.End),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = rate.currency.symbol,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    modifier = Modifier.padding(end = 4.dp),

                )
                BasicTextField(
                    value = if (isSelected) amount else rate.value.rounderNumber(),
                    onValueChange = onAmountChange,
                    enabled = isSelected,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Start),
                    modifier = Modifier.width(IntrinsicSize.Min)

                )
                if (isSelected && amount != "1") {
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .size(18.dp)
                            .clip(CircleShape)
                            .background(Color.Red.copy(alpha = 0.9f))
                            .clickable { onAmountChange("1") }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Сбросить сумму",
                            tint = Color.White,
                            modifier = Modifier
                                .padding(2.dp)
                                .size(14.dp)
                        )
                    }
                }
            }
        }
    }
    Divider(Modifier.height(1.dp), color = Color.LightGray)
}

