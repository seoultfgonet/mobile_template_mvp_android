package me.contextof.template.mvp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.contextof.template.base.BaseFragment
import me.contextof.template.dagger.HasComponent

/**
 * Date 2018. 1. 9.
 * Author Jun-hyoung, Lee
 */
abstract class MvpFragmentView<V : MvpView, P : BasePresenter<V>> : BaseFragment(), MvpView {

    protected var presenter: P? = null

    @Suppress("UNCHECKED_CAST")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        presenter = onCreatePresenter()
        presenter?.attachView(this as V)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter?.detachView()
    }

    abstract fun onCreatePresenter(): P

    override fun onSetPresenter(presenter: MvpPresenter<*>) {
        this.presenter = presenter as P
    }

    protected fun <C> getActivityComponent(componentType: Class<C>): C {
        return componentType.cast((activity as HasComponent<C>).component)
    }

}