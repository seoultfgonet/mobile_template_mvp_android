package me.contextof.template.network

import me.contextof.template.base.AndroidContext
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Date 2018. 1. 10.
 * Author Jun-hyoung, Lee
 */
class MyAppSampleApiBuilder : ApiFactory.ApiBuilder {

    override val apiType: ApiType
        get() = ApiType.MYAPP_SAMPLE

    override fun <ENDPOINT> build(clazz: Class<ENDPOINT>): ENDPOINT {
        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("https://sample.contextof.me")
                .client(AndroidContext.instance().appComponent.getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        return retrofit.create(clazz)
    }

}