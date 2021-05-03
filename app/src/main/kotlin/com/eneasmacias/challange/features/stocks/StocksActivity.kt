
package com.eneasmacias.challange.features.stocks

import android.content.Context
import android.content.Intent
import com.eneasmacias.challange.core.platform.BaseActivity

class StocksActivity : BaseActivity() {

    companion object {
        fun callingIntent(context: Context) = Intent(context, StocksActivity::class.java)
    }

    override fun fragment() = StocksFragment()
}
