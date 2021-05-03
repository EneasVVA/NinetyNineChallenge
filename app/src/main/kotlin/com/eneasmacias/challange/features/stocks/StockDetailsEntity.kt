
package com.eneasmacias.challange.features.stocks

import com.eneasmacias.challange.core.extension.empty
data class StockDetailsEntity(
    private val name: String,
    private val hot: Int,
    private val ricCode: String,
    private val category: String
) {

    companion object {
        val empty = StockDetailsEntity(
                String.empty(), 0, String.empty(), String.empty()
        )
    }

    fun toStockDetails() = StockDetails(name, hot, ricCode, category)
}
