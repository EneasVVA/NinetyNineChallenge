
package com.eneasmacias.challange.features.stocks

import com.eneasmacias.challange.core.executor.PostExecutionThread
import com.eneasmacias.challange.core.executor.ThreadExecutor
import com.eneasmacias.challange.core.interactor.SingleUseCase
import com.eneasmacias.challange.core.interactor.UseCase
import io.reactivex.Single
import javax.inject.Inject

class GetStocks
@Inject constructor(private val stocksRepository: StocksRepository, threadExecutor: ThreadExecutor, postExecutionThread: PostExecutionThread) : SingleUseCase<List<Stock>, UseCase.None>(threadExecutor, postExecutionThread) {
    override fun buildUseCaseObservable(params: UseCase.None): Single<List<Stock>> = stocksRepository.stocks()
}
