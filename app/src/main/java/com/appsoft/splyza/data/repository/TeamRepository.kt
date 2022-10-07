package com.appsoft.splyza.data.repository

import com.appsoft.splyza.data.model.InviteTeamRequest
import com.appsoft.splyza.data.model.InviteTeamResponse
import com.appsoft.splyza.data.model.TeamResponse
import com.appsoft.splyza.data.network.MockTeamServiceApi
import com.appsoft.splyza.data.network.ResourceState
import com.appsoft.splyza.data.network.TeamServiceApi
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class TeamRepository @Inject constructor(
    private val apiService: TeamServiceApi,
    private val mockTeamServiceApi: MockTeamServiceApi
) {

    suspend fun getTeam(id: String): ResourceState<TeamResponse> {
        return mockTeamServiceApi.getTeam()
    }

    suspend fun getTeamInviteUrl(
        id: String,
        role: InviteTeamRequest
    ): ResourceState<InviteTeamResponse> {
        return mockTeamServiceApi.getTeamInviteUrl(
            role = role.role
        )
    }
}