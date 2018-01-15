package me.contextof.template.mvp

/**
 * Date 2018. 1. 9.
 * Author Jun-hyoung, Lee
 */
interface MvpPresenter<V : MvpView> {

    fun attachView(view: V)

    fun detachView()
}
