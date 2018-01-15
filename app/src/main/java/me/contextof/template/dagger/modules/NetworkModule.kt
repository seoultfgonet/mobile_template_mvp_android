package me.contextof.template.dagger.modules

import dagger.Module
import dagger.Provides
import me.contextof.template.network.ApiFactory
import okhttp3.OkHttpClient
import javax.inject.Singleton

/**
 * Date 2018. 1. 10.
 * Author Jun-hyoung, Lee
 */
@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
            .addInterceptor({
                chain -> chain.proceed(chain.request())
            })
            .build()

    @Provides
    @Singleton
    fun provideRestApiFactory(): ApiFactory = ApiFactory()
}