package me.contextof.template.base

import android.app.Application
import android.content.pm.PackageInfo
import android.content.res.Resources
import me.contextof.template.dagger.components.AppComponent
import me.contextof.template.MyApplication


/**
 * Date 2018. 1. 8.
 * Author Jun-hyoung, Lee
 */
class AndroidContext private constructor(val application: MyApplication) {

    val component: AppComponent
        get() = application.appComponent

    companion object {

        private var INSTANCE: AndroidContext? = null

        internal val packageName: String
            get() = application().packageName

        internal val versionName: String
            get() {
                val pi: PackageInfo = application().packageManager.getPackageInfo(AndroidContext.packageName, 0)
                return pi.versionName
            }

        internal fun initialize(application: MyApplication) {
            INSTANCE = AndroidContext(application)
        }

        fun instance(): AndroidContext {
            if (INSTANCE == null) {
                throw IllegalStateException("Android context was not initialized yet.")
            }
            return INSTANCE as AndroidContext
        }

        fun application(): Application = instance().application

        fun string(resId: Int): String = application().getString(resId)

        fun resource(): Resources = application().resources

        fun component(): AppComponent = instance().component
    }
}