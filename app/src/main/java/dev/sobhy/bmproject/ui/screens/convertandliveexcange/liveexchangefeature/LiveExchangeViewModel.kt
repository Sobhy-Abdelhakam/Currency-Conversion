package dev.sobhy.bmproject.ui.screens.convertandliveexcange.liveexchangefeature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.sobhy.bmproject.data.Repository
import dev.sobhy.bmproject.data.model.CompareResponse
import dev.sobhy.bmproject.data.model.Currency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LiveExchangeViewModel : ViewModel() {
    private val repository = Repository()
    private val _state = MutableStateFlow(ExchangeState())
    val state = _state.asStateFlow()

    init {
        setAmountZeroToAllRecord()
    }

    fun getCurrencies() {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch(Dispatchers.IO) {
            val remoteCurrencies = repository.getCurrencies().currencies
            val localList = state.value.savedCurrencies
            val listShouldBeShow =
                compareLocalAndRemoteAndReturnViewedList(remoteCurrencies, localList)
            _state.update {
                it.copy(isLoading = false, currencyResponse = listShouldBeShow)
            }
        }
    }

    private fun compareLocalAndRemoteAndReturnViewedList(
        remoteList: List<Currency>,
        localList: List<Currency>?
    ): List<Currency> {
        return remoteList.onEach { remoteCurrency ->
            if (localList?.find { localCurrency ->
                    localCurrency.code == remoteCurrency.code
                } != null) {
                remoteCurrency.isSaved = true
            }
        }
    }

    fun saveCurrency(currency: Currency) {
        viewModelScope.launch {
            repository.insertCurrency(currency.also {
                it.isSaved = true
            })
        }
    }

    fun deleteCurrency(currency: Currency) {
        viewModelScope.launch {
            repository.deleteCurrency(currency.also {
                it.isSaved = false
            })
        }
    }

    fun getSavedCurrencies() {
        viewModelScope.launch {
            _state.update {
                it.copy(savedCurrencies = repository.getSavedCurrencies())
            }
        }
    }

    fun getCompareResponseAndSaveInDatabaseWithAmountValue(amountFrom: Double, from: String) {
        if (state.value.savedCurrencies.isNotEmpty()) {
            _state.update { it.copy(isLoading = true) }
            viewModelScope.launch {
                val listOfCodeInDb = state.value.savedCurrencies.map { currency -> currency.code }
                val compareResponse =
                    repository.getComparisonResponse(1.0, from, listOfCodeInDb)
                for (apiItem in compareResponse.comparisonRates) {
                    val existingCurrency = repository.getCurrenciesByCode(apiItem.currencyCode)
                    existingCurrency.let {
                        val newCurrency = Currency(
                            code = existingCurrency.code,
                            flagUrl = existingCurrency.flagUrl,
                            desc = existingCurrency.desc,
                            amount = apiItem.amount
                        )
                        saveCurrency(newCurrency)
                    }
                }
                _state.update {
                    it.copy(isLoading = false, compareResponse = compareResponse)
                }
            }
        }
    }

    private fun setAmountZeroToAllRecord(){
        viewModelScope.launch {
            repository.setAmountZeroToAllRecord()
        }
    }
}

data class ExchangeState(
    val isLoading: Boolean = false,
    val currencyResponse: List<Currency> = emptyList(),
    val savedCurrencies: List<Currency> = emptyList(),
    val compareResponse: CompareResponse =
        CompareResponse(comparisonRates = emptyList(), ""),
)