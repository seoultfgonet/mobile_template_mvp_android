package me.contextof.template.dagger.components

import android.app.Application

import javax.inject.Singleton

import dagger.Component
import me.contextof.template.dagger.modules.AppModule
import me.contextof.template.base.Navigator
import me.contextof.template.dagger.modules.NetworkModule
import me.contextof.template.network.ApiFactory
import okhttp3.OkHttpClient

@Singleton
@Component(modules = arrayOf(AppModule::class, NetworkModule::class))
interface AppComponent {

    fun inject(application: Application)

    fun getApplication(): Application

    fun getNavigator(): Navigator

    fun getOkHttpClient(): OkHttpClient

    fun getApiFactory(): ApiFactory
}
