package com.example.currencyconverter.data.dataSource.room.account.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.currencyconverter.data.dataSource.room.account.dbo.AccountDbo


import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllAccount(accounts: List<AccountDbo>)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSingleAccount(account: AccountDbo)

    @Query("SELECT * FROM accounts")
    suspend fun getAllAccounts(): List<AccountDbo>

    @Query("SELECT * FROM accounts")
    fun getAllAsFlow(): Flow<List<AccountDbo>>

    @Query("SELECT * FROM accounts WHERE currency_code = :code LIMIT 1")
    suspend fun getAccountByCode(code: String): AccountDbo?

    @Query("SELECT COUNT(*) == 0 FROM accounts")
    suspend fun isEmpty(): Boolean

    @Query("UPDATE accounts SET amount = :newBalance WHERE currency_code = :currencyCode")
    suspend fun updateAccountBalance(
        currencyCode: String,
        newBalance: Double
    )

}
