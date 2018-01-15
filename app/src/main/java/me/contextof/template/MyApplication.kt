package me.contextof.template

import android.support.multidex.MultiDexApplication
import com.squareup.leakcanary.LeakCanary
import me.contextof.template.base.AndroidContext
import me.contextof.template.dagger.components.AppComponent
import me.contextof.template.dagger.components.DaggerAppComponent
import me.contextof.template.dagger.modules.AppModule

/**
 * Date 2018. 1. 8.
 * Author Jun-hyoung, Lee
 */
class MyApplication : MultiDexApplication() {

    val appComponent: AppComponent
        get() = DaggerAppComponent.builder().appModule(AppModule(this)).build()

    override fun onCreate() {
        super.onCreate()

        AndroidContext.initialize(this)

        setUpInjector()
        setUpLeakCanary()
    }

    private fun setUpLeakCanary() {
        if (BuildConfig.DEBUG) {
            LeakCanary.install(this)
        }
    }

    private fun setUpInjector() {
        this.appComponent.inject(this)
    }

}