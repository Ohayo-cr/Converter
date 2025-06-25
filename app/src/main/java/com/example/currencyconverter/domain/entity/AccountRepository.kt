package com.example.currencyconverter.domain.entity


import com.example.currencyconverter.data.dataSource.room.account.dao.AccountDao
import com.example.currencyconverter.data.dataSource.room.account.dbo.AccountDbo
import javax.inject.Inject

class AccountRepository @Inject constructor(
    private val accountDao: AccountDao
) {
    suspend fun accountIsEmpty(): Boolean {
        return accountDao.isEmpty()
    }
    suspend fun insertAllAccount(accounts: List<AccountDbo>) {
        accountDao.insertAllAccount(accounts)
    }
}