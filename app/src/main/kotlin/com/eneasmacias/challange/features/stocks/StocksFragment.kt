
package com.eneasmacias.challange.features.stocks

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.eneasmacias.challange.R
import com.eneasmacias.challange.core.exception.Failure
import com.eneasmacias.challange.core.exception.Failure.NetworkConnection
import com.eneasmacias.challange.core.exception.Failure.ServerError
import com.eneasmacias.challange.core.extension.failure
import com.eneasmacias.challange.core.extension.invisible
import com.eneasmacias.challange.core.extension.observe
import com.eneasmacias.challange.core.extension.visible
import com.eneasmacias.challange.core.navigation.Navigator
import com.eneasmacias.challange.core.platform.BaseFragment
import com.eneasmacias.challange.features.stocks.StockFailure.ListNotAvailable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_stocks.*
import javax.inject.Inject

@AndroidEntryPoint
class StocksFragment : BaseFragment() {

    @Inject
    lateinit var navigator: Navigator
    @Inject
    lateinit var stocksAdapter: StocksAdapter

    private val stocksViewModel: StocksViewModel by viewModels()

    override fun layoutId() = R.layout.fragment_stocks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(stocksViewModel) {
            observe(stocks, ::renderStocksList)
            failure(failure, ::handleFailure)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView()
        loadStocksList()
    }


    private fun initializeView() {
        stockList.layoutManager = LinearLayoutManager(context)
        stockList.adapter = stocksAdapter
        stocksAdapter.itemClickListener = { stock -> navigator.showStockDetails(requireActivity(), stock) }
        stocksAdapter.deleteFavoriteClickListener = { stock -> stocksViewModel.deleteStockFavorite(stock) }
    }

    private fun loadStocksList() {
        emptyView.invisible()
        stockList.visible()
        showProgress()
        stocksViewModel.loadStocks()
    }

    private fun renderStocksList(stocks: List<StockView>?) {
        stocksAdapter.collection = stocks.orEmpty()
        hideProgress()
    }

    private fun handleFailure(failure: Failure?) {
        when (failure) {
            is NetworkConnection -> renderFailure(R.string.failure_network_connection)
            is ServerError -> renderFailure(R.string.failure_server_error)
            is ListNotAvailable -> renderFailure(R.string.failure_stocks_list_unavailable)
            else -> renderFailure(R.string.failure_server_error)
        }
    }

    private fun renderFailure(@StringRes message: Int) {
        stockList.invisible()
        emptyView.visible()
        hideProgress()
        notifyWithAction(message, R.string.action_refresh, ::loadStocksList)
    }
}
