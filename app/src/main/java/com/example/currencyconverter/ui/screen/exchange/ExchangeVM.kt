package com.example.currencyconverter.ui.screen.exchange

import androidx.lifecycle.ViewModel
import com.example.currencyconverter.data.dataSource.remote.RatesService
import com.example.currencyconverter.domain.entity.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExchangeVM @Inject constructor(
    private val ratesService: RatesService,
    private val accountRepository: AccountRepository
) : ViewModel() {

}