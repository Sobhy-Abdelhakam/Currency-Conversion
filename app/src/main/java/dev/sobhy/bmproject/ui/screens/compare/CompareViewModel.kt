package dev.sobhy.bmproject.ui.screens.compare


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.sobhy.bmproject.data.Repository
import dev.sobhy.bmproject.data.model.ConvertResponse
import dev.sobhy.bmproject.data.model.Currency
import kotlinx.coroutines.launch


class CompareViewModel : ViewModel() {


    private var currencyLiveData = MutableLiveData<List<Currency>>()
    private var currencyCompareLiveData = MutableLiveData<ConvertResponse>()
     var currencyCompareLiveData1 = MutableLiveData<ConvertResponse>()

    var from = MutableLiveData<Currency>()
    var to = MutableLiveData<Currency>()
    var _to = MutableLiveData<Currency>()
    private val repository = Repository()



        fun getCurrencies() {
        viewModelScope.launch {
            currencyLiveData.value = repository.getCurrencies().currencies }
        }

    fun compareCurrencies(from: String, to:String,amount: Double){
        viewModelScope.launch{
            currencyCompareLiveData.value = repository.getConvertResponse(from,to,amount)
        }
    }

    fun compareCurrencies1(_from: String, to:String,amount: Double){
        viewModelScope.launch{
            currencyCompareLiveData1.value = repository.getConvertResponse1(_from,to,amount)
        }
    }
    fun observeCurrenciesLivedata():LiveData<List<Currency>>{
        return currencyLiveData
    }
fun observeCurrenciesCompareLivedata():LiveData<ConvertResponse>{
        return currencyCompareLiveData
    }

    fun observeCurrenciesCompareLivedata1():LiveData<ConvertResponse>{
        return currencyCompareLiveData1
    }
    fun observeFromLivedata():LiveData<Currency>{
        return from
    }


 fun observeToLivedata():LiveData<Currency>{
        return to
    }
 fun observe_ToLivedata():LiveData<Currency>{
        return _to
    }



}