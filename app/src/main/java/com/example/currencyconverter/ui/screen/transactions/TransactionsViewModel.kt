package com.example.currencyconverter.ui.screen.transactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.domain.repository.TransactionRepository
import com.example.currencyconverter.ui.screen.transactions.component.CurrencyUtils
import com.example.currencyconverter.ui.screen.transactions.component.TransactionUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject




@HiltViewModel
class TransactionsViewModel @Inject constructor(private val repository: TransactionRepository) : ViewModel() {



    private val _transactions = MutableStateFlow<List<TransactionUiModel>>(emptyList())
    val transactions: StateFlow<List<TransactionUiModel>> = _transactions

    init {
        viewModelScope.launch {

            repository.getAllTransactions().collect { dbTransactions ->
                val uiModels = dbTransactions.map { db ->
                    TransactionUiModel(
                        from = CurrencyUtils.getByCode(db.from),
                        to = CurrencyUtils.getByCode(db.to),
                        fromAmount = db.fromAmount,
                        toAmount = db.toAmount,
                        formattedDate = formatDate(db.dateTime)
                    )
                }
                _transactions.value = uiModels
            }
        }
    }

    private fun formatDate(dateTime: LocalDateTime): String {
        return dateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))
    }
}