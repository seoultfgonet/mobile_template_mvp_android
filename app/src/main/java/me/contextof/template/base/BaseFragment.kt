package me.contextof.template.base

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


abstract class BaseFragment : Fragment() {

    @LayoutRes
    protected abstract fun getLayoutResourceId(): Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
        = inflater.inflate(getLayoutResourceId(), container, false)
}
