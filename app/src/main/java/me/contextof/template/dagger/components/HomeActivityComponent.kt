package me.contextof.template.dagger.components

import dagger.Component
import me.contextof.template.dagger.modules.ActivityModule
import me.contextof.template.dagger.scopes.PerActivity
import me.contextof.template.app.home.HomeActivityView

/**
 * Date 2018. 1. 9.
 * Author Jun-hyoung, Lee
 */
@PerActivity
@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(ActivityModule::class))
interface HomeActivityComponent : ActivityComponent {
    fun inject(activity: HomeActivityView)
}