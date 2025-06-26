package com.example.currencyconverter.ui.screen.exchange.component

import com.example.currencyconverter.domain.entity.Currency
import com.example.currencyconverter.domain.entity.ExchangeRate

fun calculateExchangeRate(
    fromCurrencyCode: String,
    toCurrencyCode: String,
    amount: String,
    value: String,
    accountBalances: Map<String, Double>
): List<ExchangeRate> {
    val fromCurrency = Currency.entries.find { it.code == fromCurrencyCode }
    val toCurrency = Currency.entries.find { it.code == toCurrencyCode }

    if (fromCurrency == null || toCurrency == null) return emptyList()

    val maneAmount = amount.toDoubleOrNull() ?: 0.0
    val secondaryAmount = value.toDoubleOrNull() ?: 0.0

    val fromBalance = accountBalances[fromCurrencyCode] ?: 0.0
    val toBalance = accountBalances[toCurrencyCode] ?: 0.0

    return listOf(
        ExchangeRate(
            secondaryCurrency = fromCurrency,
            secondaryValue = maneAmount,
            balanceAccount = fromBalance
        ),
        ExchangeRate(
            secondaryCurrency = toCurrency,
            secondaryValue = secondaryAmount,
            balanceAccount = toBalance
        )
    )
}