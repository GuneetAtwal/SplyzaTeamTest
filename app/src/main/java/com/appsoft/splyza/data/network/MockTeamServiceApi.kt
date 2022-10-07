package com.appsoft.splyza.data.network

import com.appsoft.splyza.data.model.InviteTeamResponse
import com.appsoft.splyza.data.model.Members
import com.appsoft.splyza.data.model.Plan
import com.appsoft.splyza.data.model.TeamResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class MockTeamServiceApi @Inject constructor(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun getTeam(): ResourceState<TeamResponse> = withContext(dispatcher) {
        /**
          * No real API so generating random values for team
         */
        var administrators = (0..100).random()
        var managers = (0..100).random()
        var editors = (0..100).random()
        var members = (0..100).random()
        val supporters = (0..50).random()

        var total = administrators + managers + editors + members + supporters


        var membersLimit = (0..100).random()
        var supportersLimit = (0..50).random()

        val team = TeamResponse(
            id = UUID.randomUUID().toString(), members = Members(
                total = total,
                administrators = administrators,
                managers = managers,
                editors = editors,
                members = members,
                supporters = supporters
            ), plan = Plan(
                memberLimit = membersLimit,
                supporterLimit = supportersLimit
            )
        )

        return@withContext ResourceState.Success(team)
    }

    suspend fun getTeamInviteUrl(role: String): ResourceState<InviteTeamResponse> = withContext(dispatcher) {
        /**
         * There is no real api so response will be mocked with random values
         */

        val mockInviteUrl = "{\n" +
                "\"url\": \"https://example.com/ti/${UUID.randomUUID()}\"\n" +
                "}"

        val response = InviteTeamResponse(mockInviteUrl)

        return@withContext ResourceState.Success(response)
    }
}