package dev.sobhy.bmproject.ui.screens.convertandliveexcange.ConvertScreen


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.sobhy.bmproject.data.Repository
import dev.sobhy.bmproject.data.model.ConvertResponse
import dev.sobhy.bmproject.data.model.Currency
import kotlinx.coroutines.launch


class ConvertViewModel : ViewModel() {


    private var currencyLiveData = MutableLiveData<List<Currency>>()
    private var currencyConvertLiveData = MutableLiveData<ConvertResponse>()


    var from = MutableLiveData<Currency>()
    var to = MutableLiveData<Currency>()
    private val repository = Repository()



    fun getCurrencies() {
        viewModelScope.launch {
            currencyLiveData.value = repository.getCurrencies().currencies }
    }
    fun convertCurrencies(from: String, to:String,amount: Double){
        viewModelScope.launch{
            currencyConvertLiveData.value = repository.getConvertResponse(from,to,amount)
        }
    }
    fun observeCurrenciesLivedata():LiveData<List<Currency>>{
        return currencyLiveData
    }
    fun observeCurrenciesConvertLivedata():LiveData<ConvertResponse>{
        return currencyConvertLiveData
    }
    fun observeFromLivedata():LiveData<Currency>{
        return from
    }


    fun observeToLivedata():LiveData<Currency>{
        return to
    }




}