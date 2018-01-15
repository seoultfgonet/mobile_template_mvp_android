package me.contextof.template.dagger.modules

import android.support.v4.app.Fragment

import dagger.Module
import dagger.Provides
import me.contextof.template.dagger.scopes.PerFragment

@Module
class FragmentModule(private val fragment: Fragment) {

    @Provides
    @PerFragment
    internal fun provideFragment(): Fragment {
        return this.fragment
    }
}
