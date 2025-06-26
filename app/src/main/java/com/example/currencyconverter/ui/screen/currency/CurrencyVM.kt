package com.example.currencyconverter.ui.screen.currency

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.data.dataSource.remote.RatesService
import com.example.currencyconverter.domain.repository.AccountRepository
import com.example.currencyconverter.domain.entity.ExchangeRate
import com.example.currencyconverter.data.mapper.mapToExchangeRates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class CurrencyVM @Inject constructor(
    private val ratesService: RatesService,
    private val accountRepository: AccountRepository,
) : ViewModel() {

    private val _mainCurrency = MutableStateFlow("USD")
    val mainCurrency: StateFlow<String> = _mainCurrency

    private val _mainAmount = MutableStateFlow("1")
    val mainAmount: StateFlow<String> = _mainAmount


    private val _baseRates = MutableStateFlow<List<ExchangeRate>>(emptyList())
    val baseRates: StateFlow<List<ExchangeRate>> = _baseRates

    val calculatedRates: StateFlow<List<ExchangeRate>> = combine(
        _baseRates,
        _mainAmount
    ) { rates, amountStr ->
        val amount = amountStr.toDoubleOrNull() ?: 1.0
        rates.map { rate -> rate.copy(secondaryValue = rate.
        secondaryValue* amount) }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private var currentLoadJob: Job? = null

    init {
        viewModelScope.launch {
            _mainAmount.collect { amountStr ->
                val amount = amountStr.toDoubleOrNull() ?: return@collect
                if (_baseRates.value.isNotEmpty()) {
                }
            }
        }

        loadRates(baseCurrencyCode = "USD", amount = 1.0)
    }

    private fun loadRates(baseCurrencyCode: String = "USD", amount: Double = 1.0) {
        currentLoadJob?.cancel()
        currentLoadJob = viewModelScope.launch {
            while (isActive) {
                try {
                    val result = ratesService.getRates(baseCurrencyCode, amount)
                    val accounts = accountRepository.getAllAccounts()
                    val mappedRates = mapToExchangeRates(result, accounts)
                    _baseRates.value = mappedRates
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                delay(1000)
            }
        }
    }

    fun setAmount(value: String) {
        _mainAmount.value = value
    }

    fun setNewMainCurrency(currencyCode: String) {
        if (_mainCurrency.value != currencyCode) {
            _mainCurrency.value = currencyCode
            _mainAmount.value = "1"
            viewModelScope.launch {
                loadRates(baseCurrencyCode = currencyCode, amount = 1.0)
            }
        }
    }
}