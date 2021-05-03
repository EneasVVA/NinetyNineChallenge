
package com.eneasmacias.challange.features.stocks

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface StocksApi {
    companion object;

    @GET("/favorites")
    fun stocks(): Single<StocksListEntity>

    @GET("/favorites/{stockId}")
    fun stockDetails(@Path("stockId") stockId: String): Single<StockDetailsEntity>
}
