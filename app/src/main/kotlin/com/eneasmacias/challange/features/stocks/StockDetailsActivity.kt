
package com.eneasmacias.challange.features.stocks

import android.content.Context
import android.content.Intent
import com.eneasmacias.challange.core.platform.BaseActivity

class StockDetailsActivity : BaseActivity() {

    companion object {
        private const val INTENT_EXTRA_PARAM_STOCK = "INTENT_PARAM_STOCK"

        fun callingIntent(context: Context, stock: StockView) =
            Intent(context, StockDetailsActivity::class.java).apply {
                putExtra(INTENT_EXTRA_PARAM_STOCK, stock)
            }
    }

    override fun fragment() =
        StockDetailsFragment.forStock(intent.getParcelableExtra(INTENT_EXTRA_PARAM_STOCK))
}
