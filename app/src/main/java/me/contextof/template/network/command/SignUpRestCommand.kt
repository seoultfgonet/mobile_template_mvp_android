package me.contextof.template.network.command

import io.reactivex.Observable
import me.contextof.template.base.AndroidContext
import me.contextof.template.network.ApiFactory
import me.contextof.template.network.endpoint.SampleApiService

/**
 * Date 2018. 1. 10.
 * Author Jun-hyoung, Lee
 */
class SignUpRestCommand : RestCommand() {

    override fun build(): Observable<Any> {
        val endpoint: SampleApiService = AndroidContext.instance().component.apiFactory()
                .getOrCreate(ApiFactory.API_SAMPLE, SampleApiService::class.java)

        return endpoint.signup("body")
    }

    override fun execute() {
        build().subscribe()
    }
}