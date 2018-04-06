package me.contextof.template.dagger.modules

import android.app.Application

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import io.realm.Realm
import me.contextof.template.base.Navigator

@Module
class AppModule(private val application: Application) {

    @Provides
    @Singleton
    internal fun provideApplication(): Application {
        return this.application
    }

    @Provides
    @Singleton
    internal fun provideNavigator(): Navigator {
        return Navigator()
    }

    @Provides
    @Singleton
    internal fun provideRealm(): Realm {
        return Realm.getDefaultInstance()
    }
}
