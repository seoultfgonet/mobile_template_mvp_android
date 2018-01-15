package me.contextof.template.base

import me.contextof.template.dagger.components.AppComponent
import me.contextof.template.MyApplication


/**
 * Date 2018. 1. 8.
 * Author Jun-hyoung, Lee
 */
class AndroidContext private constructor(val application: MyApplication) {

    val appComponent: AppComponent
        get() = application.appComponent

    companion object {

        private var INSTANCE: AndroidContext? = null

        internal fun initialize(application: MyApplication) {
            INSTANCE = AndroidContext(application)
        }

        fun instance() : AndroidContext {
            if (INSTANCE == null) {
                throw IllegalStateException("Android context was not initialized yet.")
            }
            return INSTANCE as AndroidContext
        }
    }
}