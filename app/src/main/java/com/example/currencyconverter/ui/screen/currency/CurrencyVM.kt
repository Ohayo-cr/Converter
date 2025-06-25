package com.example.currencyconverter.ui.screen.currency

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.data.dataSource.remote.RatesService
import com.example.currencyconverter.data.dataSource.remote.dto.RateDto
import com.example.currencyconverter.domain.entity.AccountRepository
import com.example.currencyconverter.domain.entity.ExchangeRate
import com.example.currencyconverter.domain.entity.mapToExchangeRates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyVM @Inject constructor(
    private val ratesService: RatesService,
    private val accountRepository: AccountRepository
) : ViewModel() {



    private val _baseCurrency = MutableStateFlow("USD")
    val baseCurrency: StateFlow<String> = _baseCurrency
    private val _amount = MutableStateFlow("1")
    val amount: StateFlow<String>  = _amount

    fun setAmount(value: String) {
        _amount.value = value
    }

    private val _rates = MutableStateFlow<List<ExchangeRate>>(emptyList())
    val rates: StateFlow<List<ExchangeRate>> = _rates

    // Для отмены предыдущей загрузки
    private var currentLoadJob: Job? = null
    init {
        viewModelScope.launch {
            _amount.collect { amountStr ->
                val amount = amountStr.toDoubleOrNull() ?: return@collect
                loadRates(baseCurrencyCode = _baseCurrency.value, amount = amount)
            }
        }
    }

    fun loadRates(baseCurrencyCode: String = "USD", amount: Double = 1.0) {
        currentLoadJob?.cancel()
        currentLoadJob = viewModelScope.launch {
            while (isActive) {
                try {

                    val result = ratesService.getRates(baseCurrencyCode, amount)
                    val accounts = accountRepository.getAllAccounts() // Получаем балансы из БД
                    val mappedRates = mapToExchangeRates(result, accounts) // Теперь с балансами
                    _rates.value = mappedRates
                } catch (e: Exception) {
                    // Можно добавить логирование ошибки
                    e.printStackTrace()
                }

                delay(1000)
            }
        }
    }

    fun setBaseCurrency(currencyCode: String) {
        if (_baseCurrency.value != currencyCode) {
            _baseCurrency.value = currencyCode
            _amount.value = "1"
            viewModelScope.launch {

                loadRates(baseCurrencyCode = currencyCode, amount = _amount.value.toDoubleOrNull() ?: 1.0)
            }
        }
    }

}