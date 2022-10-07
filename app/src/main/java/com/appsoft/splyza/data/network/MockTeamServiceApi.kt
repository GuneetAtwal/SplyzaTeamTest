package com.appsoft.splyza.data.network

import android.util.Log
import com.appsoft.splyza.data.model.InviteTeamResponse
import com.appsoft.splyza.data.model.Members
import com.appsoft.splyza.data.model.Plan
import com.appsoft.splyza.data.model.TeamResponse
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.util.*
import javax.inject.Inject

@ActivityRetainedScoped
class MockTeamServiceApi @Inject constructor(
    private val dispatcher: CoroutineDispatcher
) {
    suspend fun getTeam(): ResourceState<TeamResponse> = withContext(dispatcher) {
        /** No real API so generating random values for team */
        var administrators = (0..100).random()
        var managers = (0..100).random()
        var editors = (0..100).random()
        var members = (0..100).random()
        val supporters = (0..10).random()
        var total = administrators + managers + editors + members + supporters

        var membersLimit = (0..100).random()

        if (membersLimit < total - supporters) {
            membersLimit = total
        }

        var supportersLimit = (0..50).random().coerceAtLeast(supporters)

        //Todo Harcoding cases due to unavailability of APIs
        if (total%3 == 0) {
            administrators = 3
            managers = 6
            editors = 13
            members = 20

            total = administrators + managers + editors + members + supporters
            membersLimit = administrators + managers + editors + members
        } else if (total%5 == 0) {
            supportersLimit = 0
        } else if (total%7 == 0 && supporters > 0) {
            supportersLimit = supporters
        }

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

        Log.d("666", "$team")

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