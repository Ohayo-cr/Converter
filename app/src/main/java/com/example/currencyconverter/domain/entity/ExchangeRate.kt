package com.example.currencyconverter.domain.entity



data class ExchangeRate(
    val currency: Currency,
    val value: Double,
    val balance: Double? = null
)