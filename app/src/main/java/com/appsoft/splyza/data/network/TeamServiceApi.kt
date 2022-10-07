package com.appsoft.splyza.data.network

import com.appsoft.splyza.data.model.InviteTeamRequest
import com.appsoft.splyza.data.model.InviteTeamResponse
import com.appsoft.splyza.data.model.TeamResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TeamServiceApi {

    @GET("/teams/{teamId}")
    suspend fun getTeam(
        @Path("teamId") teamId: String
    ): Response<TeamResponse>

    @POST("/teams/{teamId}/invites")
    suspend fun setMemberRole(
        @Path("teamId") teamId: String,
        @Body request: InviteTeamRequest
    ): Response<InviteTeamResponse>
}