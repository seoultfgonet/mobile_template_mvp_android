package me.contextof.template.network.endpoint

import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Date 2018. 1. 10.
 * Author Jun-hyoung, Lee
 */
interface SampleApiService {

    @POST("users/new")
    fun signup(@Body any: Any): Observable<Any>
}