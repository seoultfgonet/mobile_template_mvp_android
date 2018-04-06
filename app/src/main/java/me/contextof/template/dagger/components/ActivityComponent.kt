package me.contextof.template.dagger.components

import android.app.Activity
import dagger.Component
import me.contextof.template.base.BaseActivity
import me.contextof.template.base.BaseFragment
import me.contextof.template.dagger.modules.ActivityModule
import me.contextof.template.dagger.scopes.PerActivity

@PerActivity
@Component(dependencies = [(AppComponent::class)], modules = arrayOf(ActivityModule::class))
interface ActivityComponent {

    fun inject(activity: BaseActivity)

    fun inject(fragment: BaseFragment)

    fun activity(): Activity
}
