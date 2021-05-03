
package com.eneasmacias.challange.features.stocks

import com.eneasmacias.challange.core.executor.PostExecutionThread
import com.eneasmacias.challange.core.executor.ThreadExecutor
import com.eneasmacias.challange.core.interactor.SingleUseCase
import com.eneasmacias.challange.features.stocks.GetStockDetails.Params
import io.reactivex.Single
import javax.inject.Inject

class GetStockDetails
@Inject constructor(private val stocksRepository: StocksRepository, threadExecutor: ThreadExecutor, postExecutionThread: PostExecutionThread) : SingleUseCase<StockDetails, Params>(threadExecutor, postExecutionThread) {

    data class Params(val id: String)

    override fun buildUseCaseObservable(params: Params): Single<StockDetails> = stocksRepository.stockDetails(params.id)

}
