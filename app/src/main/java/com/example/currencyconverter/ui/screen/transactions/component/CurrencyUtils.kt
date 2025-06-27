package com.example.currencyconverter.ui.screen.transactions.component

import com.example.currencyconverter.domain.entity.Currency

object CurrencyUtils {
    fun getByCode(code: String): Currency =
        Currency.values().firstOrNull { it.code == code } ?: throw IllegalArgumentException("Unknown currency code: $code")
}