package com.example.currencyconverter.ui.screen.exchange

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.data.dataSource.remote.RatesService
import com.example.currencyconverter.data.dataSource.room.account.dbo.AccountDbo
import com.example.currencyconverter.data.dataSource.room.transaction.dbo.TransactionDbo
import com.example.currencyconverter.domain.repository.AccountRepository
import com.example.currencyconverter.domain.entity.ExchangeRate
import com.example.currencyconverter.data.mapper.mapToExchangeRates
import com.example.currencyconverter.domain.repository.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class ExchangeVM @Inject constructor(
    private val ratesService: RatesService,
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _accounts = MutableStateFlow<List<AccountDbo>>(emptyList())
    val accounts: StateFlow<List<AccountDbo>> = _accounts

    private val _rates = MutableStateFlow<List<ExchangeRate>>(emptyList())
    val rates: StateFlow<List<ExchangeRate>> = _rates

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    var exchangeParams = mutableStateOf(ExchangeParams())


    init {
        _isLoading.value = true
        getAccounts()
        loadRates()
    }

    private fun loadRates() {
        viewModelScope.launch {
            while (isActive) {
                try {

                    val baseCurrency = exchangeParams.value.fromCurrency
                    val amountStr = exchangeParams.value.amount
                    val amount = amountStr.toDoubleOrNull() ?: 1.0

                    if (baseCurrency.isNotBlank()) {
                        val result = ratesService.getRates(baseCurrency, amount)

                        val fromCurrency = exchangeParams.value.fromCurrency
                        val toCurrency = exchangeParams.value.toCurrency

                        val filteredResult =
                            result.filter { it.currency == fromCurrency || it.currency == toCurrency }

                        val accounts = accountRepository.getAllAccounts()
                        val mappedRates = mapToExchangeRates(filteredResult, accounts)
                        _rates.value = mappedRates
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    _isLoading.value = _rates.value.isEmpty()
                }

                delay(1000)
            }
        }
    }

    data class ExchangeParams(
        val fromCurrency: String = "",
        val toCurrency: String = "",
        val amount: String = ""
    )

    private fun getAccounts() {
        viewModelScope.launch {
            val accounts = accountRepository.getAllAccounts()
            _accounts.value = accounts
        }
    }

    fun saveTransaction(
        toCurrency: String,
        fromCurrency: String,
        toSum: Double,
        fromSum: Double
    ) {
        viewModelScope.launch(Dispatchers.IO) {

            // 1. Получаем баланс по fromCurrency
            val debitBalance = accountRepository.getAccountByCurrency(fromCurrency)

            if (debitBalance == null || debitBalance.amount < fromSum) {
                _error.value = "Insufficient funds in the account $fromCurrency"
                launch(Dispatchers.Main) {
                    delay(5000)
                    _error.value = null
                }
                return@launch
            }
            val accountTo = accountRepository.getAccountByCurrency(toCurrency)
            if (accountTo == null) {
                accountRepository.insertAccountExists(toCurrency)
            }
            val acceptanceBalance = accountRepository.getAccountByCurrency(toCurrency)

            val transaction = TransactionDbo(
                id = 0,
                from = fromCurrency,
                to = toCurrency,
                fromAmount = fromSum,
                toAmount = toSum,
                dateTime = LocalDateTime.now()
            )

            viewModelScope.launch(Dispatchers.IO) {
                transactionRepository.insert(transaction)
            }
            if (acceptanceBalance != null) {


                accountRepository.updateAccountBalance(
                    currencyCode = toCurrency,
                    newBalance = acceptanceBalance.amount + toSum
                )
            }



            accountRepository.updateAccountBalance(
                currencyCode = fromCurrency,
                newBalance = debitBalance.amount - fromSum
            )
        }
    }
}