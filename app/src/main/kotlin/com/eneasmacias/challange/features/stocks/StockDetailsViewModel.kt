package com.eneasmacias.challange.features.stocks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eneasmacias.challange.core.interactor.DefaultSingleObserver
import com.eneasmacias.challange.core.platform.BaseViewModel
import com.eneasmacias.challange.features.stocks.GetStockDetails.Params
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StockDetailsViewModel
@Inject constructor(private val getStockDetails: GetStockDetails) : BaseViewModel() {

    private val _stockDetails: MutableLiveData<StockDetailsView> = MutableLiveData()
    val stockDetails: LiveData<StockDetailsView> = _stockDetails


    fun loadStockDetails(stockId: String) =
        getStockDetails.execute(object : DefaultSingleObserver<StockDetails>() {
            override fun onSuccess(t: StockDetails) {
                _stockDetails.value = StockDetailsView(t.name, t.hot, t.ricCode, t.category)
            }
            override fun onError(exception: Throwable) = handleFailure(exception)

        }, Params(stockId))

    override fun onCleared() {
        getStockDetails.dispose()
        super.onCleared()
    }
}
