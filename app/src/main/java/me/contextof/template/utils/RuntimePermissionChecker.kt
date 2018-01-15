package me.contextof.template.utils

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug


/**
 * Date 2018. 1. 10.
 * Author Jun-hyoung, Lee
 */
class RuntimePermissionChecker {

    interface RationaleDialogCallback {
        fun showRationaleDialog()
        fun showRationaleSettingsDialog()
    }

    companion object {
        const val RC_PERMISSION_RECORD_AUDIO: Int = 1

        private val LOG = AnkoLogger("RuntimePermissionChecker")

        fun checkSelfPermission(activity: Activity, permission: String, rationaleDialogCallback: RationaleDialogCallback, keepAlertingOk: Boolean? = null): Boolean {
            LOG.debug("checkSelfPermission permission: $permission")

            if (ContextCompat.checkSelfPermission(activity, permission)
                    != PackageManager.PERMISSION_GRANTED) {

                // Need to show an explanation?
                // Case 1. 이전에 앱이 이 권한을 요청했고 사용자가 요청을 거부한 경우, 이 메서드는 true를 반환합니다.
                // Case 2. 과거에 사용자가 권한 요청을 거절하고 권한 요청 시스템 대화상자에서 Don't ask again 옵션을
                //  선택한 경우, 이 메서드는 false를 반환합니다. 앱이 권한을 가지지 못하도록 기기 정책에서 금지하는 경우에도
                //  이 메서드는 false를 반환합니다.
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                    rationaleDialogCallback.showRationaleDialog()
                } else {
                    if (keepAlertingOk != null && keepAlertingOk) {
                        rationaleDialogCallback.showRationaleSettingsDialog()
                    }
                }

                return false
            }

            // You have permission
            return true
        }

        fun showRationaleSettingsDialog(context: Context) {
            try {
                val intent = Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                intent.data = Uri.parse("package:" + context.packageName)
                context.startActivity(intent)

            } catch (e: ActivityNotFoundException) {
                val intent = Intent(android.provider.Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS)
                context.startActivity(intent)
            }
        }

        fun requestPermissions(activity: Activity, permissions: Array<String>, requestCode: Int) {
            ActivityCompat.requestPermissions(activity, permissions,requestCode)
        }

        fun requestPermission(activity: Activity, permission: String, requestCode: Int) {
            ActivityCompat.requestPermissions(activity, arrayOf<String>(permission),requestCode)
        }

        fun verifyPermissions(grantResults: IntArray): Boolean {
            // At least one result must be checked.
            if (grantResults.isEmpty()) {
                return false
            }

            // Verify that each required permission has been granted, otherwise return false.
            for (result in grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    return false
                }
            }
            return true
        }
    }
}