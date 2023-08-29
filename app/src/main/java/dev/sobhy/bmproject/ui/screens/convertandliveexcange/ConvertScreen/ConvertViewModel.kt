package dev.sobhy.bmproject.ui.screens.convertandliveexcange.ConvertScreen


import android.util.Log
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
            try {
                currencyLiveData.value = repository.getCurrencies().currencies
            }catch (e: Exception){
                Log.e("convertViewModel", e.message.toString())
            }

        }
    }
    fun convertCurrencies(from: String, to:String,amount: Double){
        viewModelScope.launch{
            try {
                currencyConvertLiveData.value = repository.getConvertResponse(from,to,amount)
            } catch (e: Exception){
                currencyConvertLiveData.value = ConvertResponse(0.0, "")
                Log.e("convertViewModel", e.message.toString())
            }

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