
package com.eneasmacias.challange.features.stocks

import com.eneasmacias.challange.core.extension.empty

data class StockDetails(
        val name: String,
        val hot: Int,
        val ricCode: String,
        val category: String
) {

    companion object {
        val empty = StockDetails(
                String.empty(), 0, String.empty(), String.empty()
        )
    }
}
