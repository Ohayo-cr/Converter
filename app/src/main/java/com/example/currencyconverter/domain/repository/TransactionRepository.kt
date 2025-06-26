package com.example.currencyconverter.domain.repository

import com.example.currencyconverter.data.dataSource.room.transaction.dao.TransactionDao
import com.example.currencyconverter.data.dataSource.room.transaction.dbo.TransactionDbo
import javax.inject.Inject

class TransactionRepository@Inject constructor(private val transactionDao: TransactionDao) {
    suspend fun insert(transaction: TransactionDbo) {
        transactionDao.insertTransaction(transaction)
    }
}