
package com.eneasmacias.challange.features.stocks

import com.eneasmacias.challange.core.exception.Failure.NetworkConnection
import com.eneasmacias.challange.core.platform.NetworkHandler
import io.reactivecache2.Provider
import io.reactivecache2.ReactiveCache
import io.reactivex.Single
import javax.inject.Inject


interface StocksRepository {


    fun stocks(): Single<List<Stock>>
    fun stockDetails(stockId: String): Single<StockDetails>

    class Disk
        @Inject constructor(private val reactiveCache: ReactiveCache) : StocksRepository {
        companion object {
            internal val CACHE_KEY_STOCKS_LIST = "stocks_list"
            internal val CACHE_KEY_STOCK_DETAILS = "stock_details_"
        }

        override fun stocks(): Single<List<Stock>> {
            val cacheProvider: Provider<StocksListEntity> = reactiveCache.provider<StocksListEntity>().withKey(CACHE_KEY_STOCKS_LIST)
            return cacheProvider.read().map(StocksListEntity::toStockList)
        }

        override fun stockDetails(stockId: String): Single<StockDetails> {
            val cacheProvider: Provider<StockDetailsEntity> = reactiveCache.provider<StockDetailsEntity>().withKey(CACHE_KEY_STOCK_DETAILS + stockId)
            return cacheProvider.read().map(StockDetailsEntity::toStockDetails)
        }

    }

    class Cloud
        @Inject constructor(private val network: StocksService, private val reactiveCache: ReactiveCache) : StocksRepository {
        override fun stocks(): Single<List<Stock>>  {
            val cacheProvider: Provider<StocksListEntity> = reactiveCache.provider<StocksListEntity>().withKey(Disk.CACHE_KEY_STOCKS_LIST)
            return cacheProvider
                .read()
                .onErrorResumeNext {
                    return@onErrorResumeNext network.stocks().compose(cacheProvider.replace())
                }
                .onErrorResumeNext {
                    return@onErrorResumeNext Single.error(NetworkConnection)
                }
                .map(StocksListEntity::toStockList)
        }

        override fun stockDetails(stockId: String): Single<StockDetails> {
            val cacheProvider: Provider<StockDetailsEntity> = reactiveCache.provider<StockDetailsEntity>().withKey(Disk.CACHE_KEY_STOCK_DETAILS + stockId)
            return cacheProvider
                .read()
                .onErrorResumeNext {
                    return@onErrorResumeNext network.stockDetails(stockId).compose(cacheProvider.replace())
                }
                .onErrorResumeNext {
                    return@onErrorResumeNext Single.error(NetworkConnection)
                }
                .map(StockDetailsEntity::toStockDetails)
        }

    }

    class Network
    @Inject constructor(
        private val networkHandler: NetworkHandler,
        private val service: StocksService
    ) : StocksRepository {

        override fun stocks(): Single<List<Stock>> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> service.stocks().map(StocksListEntity::toStockList)
                false -> Single.error(NetworkConnection)
            }
        }

        override fun stockDetails(stockId: String): Single<StockDetails> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> service.stockDetails(stockId).map(StockDetailsEntity::toStockDetails)
                false -> Single.error(NetworkConnection)
            }
        }
    }
}
