package me.contextof.template.network

import android.support.annotation.IntDef
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.IllegalArgumentException

/**
 * Date 2018. 1. 10.
 * Author Jun-hyoung, Lee
 */
class ApiFactory {

    private val apiTypes: MutableMap<@ApiType Long, Retrofit?> = HashMap()
    private val serviceType: MutableMap<Class<*>, Any?> = HashMap()

    @Suppress("UNCHECKED_CAST")
    fun <SERVICE> getOrCreate(@ApiType type: Long, clazz: Class<SERVICE>): SERVICE {
        var service: SERVICE? = serviceType[clazz] as SERVICE?
        service?.let {
            return service!!
        }

        var endpoint: Retrofit? = apiTypes[type]
        if (endpoint == null) {
            endpoint = createApi(type)
            apiTypes[type] = endpoint
        }

        if (service == null) {
            service = endpoint.create(clazz)
            serviceType[clazz] = service
        }

        return service!!
    }

    private fun createApi(@ApiType type: Long): Retrofit = when (type) {
        API_SAMPLE -> {
            SampleApiBuilder().build()
        }
        else -> {
            throw IllegalArgumentException("Illegal api type")
        }
    }

    fun <ERROR> parseError(@ApiType type: Long, response: Response<*>, clazz: Class<ERROR>): ERROR {
        var endpoint: Retrofit? = apiTypes[type]
        if (endpoint == null) {
            endpoint = createApi(type)
            apiTypes[type] = endpoint
        }

        val errorConverter = endpoint.responseBodyConverter<ERROR>(clazz,
                arrayOfNulls<Annotation>(0))

        return errorConverter.convert(response.errorBody()!!)

    }

    interface ApiBuilder {
        @ApiType
        val apiType: Long

        fun build(): Retrofit
    }

    @Target(AnnotationTarget.TYPE, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.PROPERTY)
    @IntDef(API_SAMPLE)
    @Retention(AnnotationRetention.SOURCE)
    annotation class ApiType

    companion object {
        const val API_SAMPLE: Long = 0

        internal const val API_SAMPLE_BASE_URL: String = "https://api.sample.io/v1/"
    }
}