package com.example.currencyconverter.domain.repository


import com.example.currencyconverter.data.dataSource.room.account.dao.AccountDao
import com.example.currencyconverter.data.dataSource.room.account.dbo.AccountDbo
import java.math.BigDecimal
import javax.inject.Inject

class AccountRepository @Inject constructor(
    private val accountDao: AccountDao
) {
    suspend fun getAllAccounts(): List<AccountDbo> {
        return accountDao.getAllAccounts()
    }
    suspend fun accountIsEmpty(): Boolean {
        return accountDao.isEmpty()
    }
    suspend fun insertAllAccount(accounts: List<AccountDbo>) {
        accountDao.insertAllAccount(accounts)
    }
    suspend fun getAccountByCurrency(currencyCode: String): AccountDbo? {
        return accountDao.getAccountByCode(currencyCode)
    }
    suspend fun updateAccountBalance(
        currencyCode: String,
        newBalance: Double
    ) {
        accountDao.updateAccountBalance(currencyCode, newBalance)
    }
    suspend fun insertAccountExists(currencyCode: String) {
        val existing = getAccountByCurrency(currencyCode)
        if (existing == null) {
            accountDao.insertSingleAccount(AccountDbo(code = currencyCode, amount = 0.0))
        }
    }
}