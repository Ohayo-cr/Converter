package com.example.currencyconverter.ui.screen.transactions.component

import com.example.currencyconverter.domain.entity.Currency

data class TransactionUiModel(
    val from: Currency,
    val to: Currency,
    val fromAmount: Double,
    val toAmount: Double,
    val formattedDate: String
)