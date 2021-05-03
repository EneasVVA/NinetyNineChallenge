
package com.eneasmacias.challange.features.stocks

import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StocksService
@Inject constructor(retrofit: Retrofit) : StocksApi {
    private val stocksApi by lazy {
        retrofit.create(StocksApi::class.java) }

    override fun stocks() = stocksApi.stocks()
    override fun stockDetails(stockId: String) = stocksApi.stockDetails(stockId)
}
