package com.example.currencyconverter.domain.entity



data class ExchangeRate(
    val secondaryCurrency: Currency,
    val secondaryValue: Double,
    val balanceAccount: Double? = null
)