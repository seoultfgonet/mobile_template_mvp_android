package me.contextof.template.dagger.components

import android.support.v4.app.Fragment

import dagger.Component
import me.contextof.template.base.BaseFragment
import me.contextof.template.dagger.modules.FragmentModule
import me.contextof.template.dagger.scopes.PerFragment

@PerFragment
@Component(dependencies = [(AppComponent::class)], modules = arrayOf(FragmentModule::class))
interface FragmentComponent {

    fun inject(fragment: BaseFragment)

    fun fragment(): Fragment
}
