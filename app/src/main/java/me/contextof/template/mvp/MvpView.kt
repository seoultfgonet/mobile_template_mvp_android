package me.contextof.template.mvp


/**
 * Date 2018. 1. 9.
 * Author Jun-hyoung, Lee
 */
interface MvpView {

    fun onSetPresenter(presenter: MvpPresenter<*>)
}