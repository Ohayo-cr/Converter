package com.example.currencyconverter.domain.entity

import com.example.currencyconverter.data.dataSource.remote.dto.RateDto
import com.example.currencyconverter.data.dataSource.room.account.dbo.AccountDbo


fun mapToExchangeRates(
    dtoList: List<RateDto>,
    accountDboList: List<AccountDbo>
): List<ExchangeRate> {
    // Создаем Map для быстрого доступа к балансам по коду валюты
    val balanceMap = accountDboList.associateBy({ it.code }, { it.amount })

    return dtoList.mapNotNull { dto ->
        val currency = Currency.entries.find { it.code == dto.currency }
        if (currency != null) {
            // Ищем баланс по коду валюты, если нет — null
            val balance = balanceMap[dto.currency]
            ExchangeRate(currency, dto.value, balance)
        } else {
            null
        }
    }
}