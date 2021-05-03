
package com.eneasmacias.challange.core.di

import android.content.Context
import com.eneasmacias.challange.BuildConfig
import com.eneasmacias.challange.core.executor.JobExecutor
import com.eneasmacias.challange.core.executor.PostExecutionThread
import com.eneasmacias.challange.core.executor.ThreadExecutor
import com.eneasmacias.challange.features.stocks.StocksRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.reactivecache2.ReactiveCache
import io.reactivex.android.schedulers.AndroidSchedulers
import io.victoralbertos.jolyglot.GsonSpeaker
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    @Singleton
    fun provideApplicationContext(@ApplicationContext context: Context): Context = context

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://challenge.ninetynine.com/")
            .client(createClient())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun createClient(): OkHttpClient {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
        }
        return okHttpClientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideThreadExecutor(jobExecutor: JobExecutor): ThreadExecutor = jobExecutor

    @Provides
    @Singleton
    fun providePostExecutionThread(): PostExecutionThread = PostExecutionThread { AndroidSchedulers.mainThread() }

    @Provides
    @Singleton
    fun provideRxCache(context: Context) : ReactiveCache = ReactiveCache.Builder().using(
        context.cacheDir,
        GsonSpeaker()
    )

    @Provides
    @Singleton
    fun provideStocksRepository(dataSource: StocksRepository.Cloud): StocksRepository = dataSource
}
