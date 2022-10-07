package com.appsoft.splyza.data.di

import com.appsoft.splyza.data.network.MockTeamServiceApi
import com.appsoft.splyza.data.network.TeamServiceApi
import com.appsoft.splyza.data.repository.TeamRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideTeamRepository(
        apiService: TeamServiceApi,
        mockTeamServiceApi: MockTeamServiceApi
    ): TeamRepository {
        return TeamRepository(apiService,mockTeamServiceApi)
    }
}