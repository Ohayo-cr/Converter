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
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
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
import com.example.currencyconverter.ui.screen.currency.component.RateItem
import com.example.currencyconverter.ui.utils.rounderNumber


@Composable
fun CurrencyScreen(
    currencyViewModel: CurrencyVM = hiltViewModel()
) {
    val rates by currencyViewModel.rates.collectAsState()
    val baseCurrency by currencyViewModel.baseCurrency.collectAsState()
    val amount by currencyViewModel.amount.collectAsState()
    val listState = rememberLazyListState()



    when {
        rates.isEmpty() -> CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        else -> {
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)) {
                LazyColumn(state = listState) {
                    items(items = rates, key = { it.currency.code }) { rate ->
                            RateItem(
                                rate = rate,
                                baseCurrency = baseCurrency,
                                onClick = {
                                    currencyViewModel.setBaseCurrency(rate.currency.code)
                                },
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



