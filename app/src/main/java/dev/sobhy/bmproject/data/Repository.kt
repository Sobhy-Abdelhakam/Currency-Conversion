package dev.sobhy.bmproject.data

import dev.sobhy.bmproject.data.local.CurrencyDatabase
import dev.sobhy.bmproject.data.model.Currency

import dev.sobhy.bmproject.data.remote.ApiService
import dev.sobhy.bmproject.data.remote.RetrofitClient

class Repository(
    private val apiService: ApiService = RetrofitClient().apiService,
    private val database: CurrencyDatabase = CurrencyDatabase.invoke()

) {
    suspend fun getCurrencies() = apiService.getCurrencies()
    suspend fun getConvertResponse(from: String, to: String, amount: Double) =
        apiService.getConvertResponse(from, to, amount)
    suspend fun getConvertResponse1(from: String, to: String, amount: Double) =
        apiService.getConvertResponse1(from, to, amount)
    suspend fun getComparisonResponse(amount: Double, from: String, list: List<String>) =
        apiService.getComparisonResponse(amount, from, list)
    suspend fun insertCurrency(currency: Currency) = database.currencyDao().insertCurrency(currency)
    suspend fun deleteCurrency(currency: Currency) = database.currencyDao().deleteCurrency(currency)
    suspend fun getSavedCurrencies() = database.currencyDao().getSavedCurrencies()
    suspend fun getCurrenciesByCode(code: String) = database.currencyDao().getCurrenciesByCode(code)
    suspend fun setAmountZeroToAllRecord() = database.currencyDao().setAmountZeroToAllRecord()


}