package com.example.currencyconverter.ui.screen.transactions


import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.currencyconverter.ui.screen.transactions.component.TransactionItem


@Composable
fun TransactionsScreen(viewModel: TransactionsViewModel = hiltViewModel()) {
    val transactions by viewModel.transactions.collectAsState()

    LazyColumn(modifier = Modifier.padding(8.dp)) {
        items(transactions) { transaction ->
            TransactionItem(transaction)
        }
    }
}

