package me.contextof.template.app.home

import android.os.Bundle
import me.contextof.template.mvp.MvpView

/**
 * Date 2018. 1. 9.
 * Author Jun-hyoung, Lee
 */
interface HomeView : MvpView {
    fun showHome(savedInstanceState: Bundle?)
}