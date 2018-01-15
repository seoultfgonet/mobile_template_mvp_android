package me.contextof.template.network

/**
 * Date 2018. 1. 10.
 * Author Jun-hyoung, Lee
 */
class ApiFactory {

    private val apiMap: HashMap<ApiType, Any> = HashMap()

    @Suppress("UNCHECKED_CAST")
    fun <ENDPOINT> getOrCreate(type: ApiType, clazz: Class<ENDPOINT>): ENDPOINT {
        var endpoint = this.apiMap[type] as ENDPOINT
        if (endpoint == null) {
            when (type) {
                ApiType.MYAPP_SAMPLE -> {
                    endpoint = MyAppSampleApiBuilder().build(clazz)
                }
            }
        }
        return endpoint
    }

    interface ApiBuilder {

        val apiType: ApiType

        fun <ENDPOINT> build(clazz: Class<ENDPOINT>): ENDPOINT
    }
}