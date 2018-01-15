package me.contextof.template.network.command

import io.reactivex.Observable
import me.contextof.template.base.AndroidContext
import me.contextof.template.network.ApiType
import me.contextof.template.network.endpoint.MyAppSampleEndpoint

/**
 * Date 2018. 1. 10.
 * Author Jun-hyoung, Lee
 */
class MyAppSampleSignUpRestCommand : RestCommand() {

    override fun build(): Observable<Any> {
        val endpoint: MyAppSampleEndpoint = AndroidContext.instance().appComponent.getApiFactory()
                .getOrCreate(ApiType.MYAPP_SAMPLE, MyAppSampleEndpoint::class.java)

        return endpoint.signup("body")
    }

    override fun execute() {
        build().subscribe()
    }
}