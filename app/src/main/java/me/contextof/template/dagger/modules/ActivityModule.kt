package me.contextof.template.dagger.modules

import android.app.Activity

import dagger.Module
import dagger.Provides
import me.contextof.template.dagger.scopes.PerActivity

@Module
class ActivityModule(private val activity: Activity) {

    @Provides
    @PerActivity
    internal fun provideActivity(): Activity {
        return this.activity
    }
}
