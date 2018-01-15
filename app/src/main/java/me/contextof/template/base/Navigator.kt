package me.contextof.template.base

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import me.contextof.template.app.home.HomeActivityView
import org.jetbrains.anko.startActivity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Navigator @Inject constructor() {

    fun navigateToHomeActivityView(context: Context) {
        context.startActivity<HomeActivityView>()
    }

    fun navigateToAppSettingsActivity(context: Context) {
        try {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.data = Uri.parse("package:" + context.packageName)
            context.startActivity(intent)

        } catch (e: ActivityNotFoundException) {
            val intent = Intent(Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS)
            context.startActivity(intent)
        }
    }
}
