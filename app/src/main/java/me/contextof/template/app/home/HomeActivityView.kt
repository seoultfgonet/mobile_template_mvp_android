package me.contextof.template.app.home

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_home.*
import me.contextof.template.dagger.HasComponent
import me.contextof.template.dagger.components.DaggerHomeActivityComponent
import me.contextof.template.dagger.components.HomeActivityComponent
import me.contextof.template.dagger.modules.ActivityModule
import me.contextof.template.mvp.BasePresenter
import me.contextof.template.mvp.BaseView
import me.contextof.template.mvp.MvpActivityView
import me.contextof.template.R
import me.contextof.template.utils.RuntimePermissionChecker
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.PermissionRequest

class HomeActivityView : MvpActivityView<BaseView, BasePresenter<BaseView>>(),
        HasComponent<HomeActivityComponent>,
        EasyPermissions.PermissionCallbacks {

    private var component: HomeActivityComponent? = null
    private val log = AnkoLogger("home")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)

        if (savedInstanceState == null) {
//            supportFragmentManager.beginTransaction()
//                    .replace(R.id.fragment_container, TalkListFragment())
//                    .commit()
        }

        fab.setOnClickListener {
            checkSelfPermission()
        }
    }

    override fun onResume() {
        super.onResume()

        log.debug("Request permission onResume")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {

        }
    }

    override fun onCreatePresenter(): BasePresenter<BaseView> {
        return BasePresenter<BaseView>()
    }

    override fun getComponent(): HomeActivityComponent {
        if (component == null) {
            component = DaggerHomeActivityComponent.builder()
                    .appComponent(appComponent)
                    .activityModule(ActivityModule(this))
                    .build()
        }
        return component as HomeActivityComponent
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        Toast.makeText(applicationContext, "Audio access onPermissionsDenied", Toast.LENGTH_SHORT).show()
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        Toast.makeText(applicationContext, "Audio access onPermissionsGranted", Toast.LENGTH_SHORT).show()
    }

    @AfterPermissionGranted(RuntimePermissionChecker.RC_PERMISSION_RECORD_AUDIO)
    fun checkSelfPermission() {
        if (!EasyPermissions.hasPermissions(this, Manifest.permission.RECORD_AUDIO)) {
            EasyPermissions.requestPermissions(
                    PermissionRequest.Builder(this, RuntimePermissionChecker.RC_PERMISSION_RECORD_AUDIO, Manifest.permission.RECORD_AUDIO)
                            .setRationale("This app needs access to your audio so you can record you voice.")
                            .setPositiveButtonText("ok")
                            .setNegativeButtonText("cancel")
                            .build())
        }
    }

}
