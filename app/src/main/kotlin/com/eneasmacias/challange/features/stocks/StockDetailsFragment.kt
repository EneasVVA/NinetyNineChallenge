
package com.eneasmacias.challange.features.stocks

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.eneasmacias.challange.R
import com.eneasmacias.challange.core.exception.Failure
import com.eneasmacias.challange.core.exception.Failure.NetworkConnection
import com.eneasmacias.challange.core.exception.Failure.ServerError
import com.eneasmacias.challange.core.extension.*
import com.eneasmacias.challange.core.platform.BaseFragment
import com.eneasmacias.challange.features.stocks.StockFailure.NonExistentStock
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_stock_details.*

@AndroidEntryPoint
class StockDetailsFragment : BaseFragment() {

    companion object {
        private const val PARAM_STOCK = "param_stock"

        fun forStock(stock: StockView?) = StockDetailsFragment().apply {
            arguments = bundleOf(PARAM_STOCK to stock)
        }
    }
    
    private val stockDetailsViewModel by viewModels<StockDetailsViewModel>()

    override fun layoutId() = R.layout.fragment_stock_details

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(stockDetailsViewModel) {
            observe(stockDetails, ::renderStockDetails)
            failure(failure, ::handleFailure)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (firstTimeCreated(savedInstanceState)) {
            stockDetailsViewModel.loadStockDetails((arguments?.get(PARAM_STOCK) as StockView).id)
        }
    }

    override fun onBackPressed() {

    }

    private fun renderStockDetails(stock: StockDetailsView?) {
        stock?.let {
            with(stock) {
                tvStockValueName.text = name
                tvStockValueHot.text = hot.toString()
                tvStockValueRicCode.text = ricCode
                tvStockValueCategory.text = category
            }
        }
    }

    private fun handleFailure(failure: Failure?) {
        when (failure) {
            is NetworkConnection -> {
                notify(R.string.failure_network_connection); close()
            }
            is ServerError -> {
                notify(R.string.failure_server_error); close()
            }
            is NonExistentStock -> {
                notify(R.string.failure_stock_non_existent); close()
            }
            else -> {
                notify(R.string.failure_server_error); close()
            }
        }
    }
}
