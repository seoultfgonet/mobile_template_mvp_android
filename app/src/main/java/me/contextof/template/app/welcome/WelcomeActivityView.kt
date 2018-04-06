package me.contextof.template.app.welcome

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.Toast
import me.contextof.template.dagger.components.ActivityComponent
import me.contextof.template.dagger.components.DaggerActivityComponent
import me.contextof.template.dagger.modules.ActivityModule
import me.contextof.template.mvp.BasePresenter
import me.contextof.template.mvp.MvpActivityView
import me.contextof.template.R
import me.contextof.template.dagger.HasComponent
import me.contextof.template.utils.RuntimePermissionChecker
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.PermissionRequest

class WelcomeActivityView : MvpActivityView<WelcomeView, BasePresenter<WelcomeView>>(),
        HasComponent<ActivityComponent>,
        EasyPermissions.PermissionCallbacks,
        WelcomeView {

    private var component: ActivityComponent? = null
    private val handler = Handler()
    private val homeViewNavigator = Runnable {
        Log.v("HOME", "> going to home view")
        appComponent.navigator().navigateToHomeActivityView(this@WelcomeActivityView)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getComponent().inject(this)

        setContentView(R.layout.activity_welcome)
    }

    override fun onResume() {
        super.onResume()

        handler.post({ checkSelfPermission() })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {

        }
    }

    override fun onCreatePresenter(): BasePresenter<WelcomeView> {
        return BasePresenter<WelcomeView>()
    }

    override fun getComponent(): ActivityComponent {
        if (component == null) {
            component = DaggerActivityComponent.builder()
                    .appComponent(appComponent)
                    .activityModule(ActivityModule(this))
                    .build()
        }
        return component as ActivityComponent
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)

//        when (requestCode) {
//            RuntimePermissionChecker.RC_PERMISSION_RECORD_AUDIO -> {
//                Log.v("HOME", "> Runtime permission result")
//
//                if (RuntimePermissionChecker.verifyPermissions(grantResults)) {
//                    Log.v("HOME", "> Runtime permission result: granted")
//
////                    Snackbar.make(rootContainerView, "Runtime permission result: granted", Snackbar.LENGTH_INDEFINITE)
////                            .show()
//
//                } else {
//                    Log.v("HOME", "> Runtime permission result: denied")
////                    Snackbar.make(rootContainerView, "Runtime permission result: denied", Snackbar.LENGTH_INDEFINITE)
////                            .show()
//                }
//
//                splash()
//            }
//        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        Toast.makeText(applicationContext, "Audio access onPermissionsDenied", Toast.LENGTH_SHORT).show()
//        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
//            AppSettingsDialog.Builder(this).build().show()
//        }
        splash()
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        Toast.makeText(applicationContext, "Audio access onPermissionsGranted", Toast.LENGTH_SHORT).show()
        splash()
    }

    private fun checkSelfPermission() {
//        if (!EasyPermissions.hasPermissions(this, Manifest.permission.RECORD_AUDIO)) {
////            EasyPermissions.requestPermissions(this,
////                    "permission_rationale_audio",
////                    RuntimePermissionChecker.RC_PERMISSION_RECORD_AUDIO,
////                    Manifest.permission.RECORD_AUDIO)
//
//            EasyPermissions.requestPermissions(
//                    PermissionRequest.Builder(this, RuntimePermissionChecker.RC_PERMISSION_RECORD_AUDIO, Manifest.permission.RECORD_AUDIO)
//                            .build())
//        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {

            // Need to show an explanation?
            // Case 1. 이전에 앱이 이 권한을 요청했고 사용자가 요청을 거부한 경우, 이 메서드는 true를 반환합니다.
            // Case 2. 과거에 사용자가 권한 요청을 거절하고 권한 요청 시스템 대화상자에서 Don't ask again 옵션을
            //  선택한 경우, 이 메서드는 false를 반환합니다. 앱이 권한을 가지지 못하도록 기기 정책에서 금지하는 경우에도
            //  이 메서드는 false를 반환합니다.
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO)) {
                Toast.makeText(applicationContext, "Audio access true", Toast.LENGTH_SHORT).show()

                // permission is not needed for welcome view
                splash()

            } else {
                Toast.makeText(applicationContext, "Audio access false", Toast.LENGTH_SHORT).show()

                if (!EasyPermissions.somePermissionPermanentlyDenied(this, arrayListOf(Manifest.permission.RECORD_AUDIO))) {
                    EasyPermissions.requestPermissions(
                            PermissionRequest.Builder(this, RuntimePermissionChecker.RC_PERMISSION_RECORD_AUDIO, Manifest.permission.RECORD_AUDIO)
                                    .build())
                } else {
                    splash()
                }
            }
        }
    }

    override fun splash() {
        handler.postDelayed(homeViewNavigator, 2000)
    }
}
