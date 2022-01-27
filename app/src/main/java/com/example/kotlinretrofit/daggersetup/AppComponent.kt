package com.example.kotlinretrofit.daggersetup

import com.example.kotlinretrofit.RecyclerListActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ViewModelModule::class])
interface AppComponent{
    fun inject(recyclerListActivity: RecyclerListActivity)
}