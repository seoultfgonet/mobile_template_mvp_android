package me.contextof.template.mvp

import java.lang.ref.WeakReference

/**
 * Date 2018. 1. 9.
 * Author Jun-hyoung, Lee
 */
open class BasePresenter<V : MvpView> : MvpPresenter<V> {

    private var weakReference: WeakReference<V>? = null

    val view: V?
        get() = weakReference?.get()

    private val isViewAttached: Boolean
        get() = weakReference != null && weakReference!!.get() != null

    override fun attachView(view: V) {
        if (!isViewAttached) {
            weakReference = WeakReference(view)
            view.onSetPresenter(this)
        }
    }

    override fun detachView() {
        weakReference?.clear()
        weakReference = null
    }
}