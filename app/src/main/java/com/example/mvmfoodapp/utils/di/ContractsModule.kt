package com.example.mvmfoodapp.utils.di

import androidx.fragment.app.Fragment
import com.example.mvmfoodapp.ui.detail.DetailContracts
import com.example.mvmfoodapp.ui.home.HomeContracts
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
object ContractsModule {

    @Provides
    fun proFragments(fragment: Fragment): HomeContracts.View {
        return fragment as HomeContracts.View
    }

    @Provides
    fun proFragmentsDetail(fragment: Fragment): DetailContracts.View {
        return fragment as DetailContracts.View
    }
}