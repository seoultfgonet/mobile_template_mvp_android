package me.contextof.template.network

import me.contextof.template.base.AndroidContext
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Date 2018. 1. 10.
 * Author Jun-hyoung, Lee
 */
class SampleApiBuilder : ApiFactory.ApiBuilder {

    override val apiType: Long
        get() = ApiFactory.API_SAMPLE

    override fun build(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(ApiFactory.API_SAMPLE_BASE_URL)
                .client(AndroidContext.instance().component.okHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

}