package com.example.currencyconverter.domain.entity

import com.example.currencyconverter.data.dataSource.remote.dto.RateDto

fun mapToExchangeRates(dtoList: List<RateDto>): List<ExchangeRate> {
    return dtoList.mapNotNull { dto ->
        val currency = Currency.entries.find { it.code == dto.currency }
        if (currency != null) {
            ExchangeRate(currency, dto.value)
        } else {
            null // или можно игнорировать, если такой валюты нет
        }
    }
}