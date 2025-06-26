package com.example.currencyconverter.data.mapper

import com.example.currencyconverter.data.dataSource.remote.dto.RateDto
import com.example.currencyconverter.data.dataSource.room.account.dbo.AccountDbo
import com.example.currencyconverter.domain.entity.Currency
import com.example.currencyconverter.domain.entity.ExchangeRate


fun mapToExchangeRates(
    dtoList: List<RateDto>,
    accountDboList: List<AccountDbo>
): List<ExchangeRate> {
    val balanceMap = accountDboList.associateBy({ it.code }, { it.amount })
    return dtoList.mapNotNull { dto ->

        val currency = Currency.entries.find { it.code == dto.currency }
        if (currency != null) {
            val balance = balanceMap[dto.currency]
            ExchangeRate(currency, dto.value, balance)
        } else {
            null
        }
    }
}