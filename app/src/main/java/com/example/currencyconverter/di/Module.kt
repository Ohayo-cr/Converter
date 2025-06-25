package com.example.currencyconverter.di

import android.content.Context
import androidx.room.Room
import com.example.currencyconverter.data.dataSource.remote.RatesService
import com.example.currencyconverter.data.dataSource.remote.RemoteRatesServiceImpl
import com.example.currencyconverter.data.dataSource.room.ConverterDatabase
import com.example.currencyconverter.data.dataSource.room.account.dao.AccountDao
import com.example.currencyconverter.data.dataSource.room.transaction.dao.TransactionDao
import com.example.currencyconverter.domain.entity.AccountRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): ConverterDatabase {
        return Room.databaseBuilder(
            context,
            ConverterDatabase::class.java,
            ConverterDatabase.DATABASE_NAME
        ).build()
    }
    @Provides
    @Singleton
    fun provideTransactionDao(appDatabase: ConverterDatabase): TransactionDao {
        return appDatabase.transactionDao()
    }
    @Provides
    @Singleton
    fun provideAccountDao(appDatabase: ConverterDatabase): AccountDao {
        return appDatabase.accountDao()
    }
    @Provides
    @Singleton
    fun provideRatesService(): RatesService {
        return RemoteRatesServiceImpl()
    }
    @Provides
    @Singleton
    fun provideAccountRepository(accountDao: AccountDao): AccountRepository {
        return AccountRepository(accountDao)
    }
}
