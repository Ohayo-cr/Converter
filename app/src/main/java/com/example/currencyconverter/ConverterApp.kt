package com.example.currencyconverter

import android.app.Application
import com.example.currencyconverter.data.dataSource.room.DefaultAccount
import com.example.currencyconverter.domain.entity.AccountRepository
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class ConverterApp : Application() {
    @Inject
    lateinit var accountRepository: AccountRepository

    override fun onCreate() {
        super.onCreate()

        // Инициализация базовых категорий
        MainScope().launch {
            if (accountRepository.accountIsEmpty()) {
                accountRepository.insertAllAccount(DefaultAccount.DEFAULT_ACCOUNT)
            }

        }
    }
}