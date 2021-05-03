
package com.eneasmacias.challange.features.stocks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eneasmacias.challange.core.interactor.DefaultSingleObserver
import com.eneasmacias.challange.core.interactor.UseCase
import com.eneasmacias.challange.core.platform.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.Disposable
import org.reactivestreams.Subscription
import javax.inject.Inject

@HiltViewModel
class StocksViewModel
@Inject constructor(private val getStocks: GetStocks) : BaseViewModel() {

    private val _stocks: MutableLiveData<List<StockView>> = MutableLiveData()
    val stocks: LiveData<List<StockView>> = _stocks

    fun loadStocks() {
        getStocks.execute(object : DefaultSingleObserver<List<Stock>>() {
            override fun onSuccess(t: List<Stock>) {
                _stocks.value = t.map { StockView(it.id) }
            }
            override fun onError(exception: Throwable) = handleFailure(exception)

        }, UseCase.None())
    }

    fun deleteStockFavorite(stock: StockView) {
        _stocks.value = _stocks.value?.filter { stock.id != it.id }
    }

    override fun onCleared() {
        getStocks.dispose()
        super.onCleared()
    }
}
