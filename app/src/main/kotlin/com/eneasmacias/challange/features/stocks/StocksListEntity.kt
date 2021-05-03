
package com.eneasmacias.challange.features.stocks

data class StocksListEntity(private val result: List<String>) {
    fun toStockList() = result.map { Stock(it) }

    companion object {
        val empty = StocksListEntity(emptyList())
    }
}
