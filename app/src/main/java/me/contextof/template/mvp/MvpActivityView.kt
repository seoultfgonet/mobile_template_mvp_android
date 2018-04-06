package me.contextof.template.mvp

import android.os.Bundle
import me.contextof.template.base.BaseActivity
import me.contextof.template.dagger.components.AppComponent
import me.contextof.template.MyApplication

abstract class MvpActivityView<V : MvpView, P : BasePresenter<V>> : BaseActivity(), MvpView {

    protected var presenter: P? = null

    protected val appComponent: AppComponent
        get() = (application as MyApplication).appComponent

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = onCreatePresenter()
        presenter?.attachView(this as V)
    }

    override fun onDestroy() {
        super.onDestroy()
        this.presenter?.detachView()
    }

    abstract fun onCreatePresenter(): P

    override fun onSetPresenter(presenter: MvpPresenter<*>) {
        @Suppress("UNCHECKED_CAST")
        this.presenter = presenter as P
    }
}
