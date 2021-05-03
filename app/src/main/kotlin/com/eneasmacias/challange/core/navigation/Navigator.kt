
package com.eneasmacias.challange.core.navigation

import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.eneasmacias.challange.features.stocks.StockDetailsActivity
import com.eneasmacias.challange.features.stocks.StockView
import com.eneasmacias.challange.features.stocks.StocksActivity
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class Navigator
@Inject constructor() {

    fun showMain(context: Context) {
        showStocks(context)
    }

    private fun showStocks(context: Context) =
        context.startActivity(StocksActivity.callingIntent(context))

    fun showStockDetails(activity: FragmentActivity, stockView: StockView) {
        val intent = StockDetailsActivity.callingIntent(activity, stockView)
        activity.startActivity(intent)
    }
}


