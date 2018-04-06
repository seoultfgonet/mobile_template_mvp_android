package me.contextof.template

import android.Manifest
import android.os.Environment
import android.support.multidex.MultiDexApplication
import android.util.Log
import com.squareup.leakcanary.LeakCanary
import io.realm.Realm
import io.realm.RealmConfiguration
import me.contextof.template.base.AndroidContext
import me.contextof.template.dagger.components.AppComponent
import me.contextof.template.dagger.components.DaggerAppComponent
import me.contextof.template.dagger.modules.AppModule
import me.contextof.template.utils.LogTag
import pub.devrel.easypermissions.EasyPermissions
import java.io.File
import java.io.IOException

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
        setUpRealm()
        setUpLeakCanary()
    }

    private fun setUpLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }

        if (BuildConfig.DEBUG) {
            LeakCanary.install(this)
        }
    }

    private fun setUpInjector() {
        this.appComponent.inject(this)
    }

    private fun setUpRealm() {
        Realm.init(this)

        val realmConfiguration = RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build()
        Realm.setDefaultConfiguration(realmConfiguration)

        copyRealmToLocal()
    }

    private fun copyRealmToLocal() {
        if (BuildConfig.DEBUG) {
            val realm = Realm.getDefaultInstance()
            try {
                if (!isPermissionAllowed()) {
                    return
                }

                val file = File(Environment.getExternalStorageDirectory(), Realm.DEFAULT_REALM_NAME)
                if (file.exists()) {
                    file.delete()
                }

                Log.i(LogTag.APP,"APPS> realm file path: ${realm.path} exists(${File(realm.path).exists()})")

                if (File(realm.path).exists()) {
                    realm.writeCopyTo(file)

                    Log.i(LogTag.APP,"APPS> realm file copied to: ${file.absolutePath}")
                }

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun isPermissionAllowed(): Boolean {
        val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE)

        return EasyPermissions.hasPermissions(this, *permissions)
    }

}