package com.example.currencyconverter.data.dataSource.room

import com.example.currencyconverter.data.dataSource.room.account.dbo.AccountDbo

object DefaultAccount {
    val DEFAULT_ACCOUNT = listOf(
        AccountDbo("RUB", 75000.00),
        )
}