package com.appsoft.splyza.data.model

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class TeamResponse(
    @Json(name = "id") val id: String,
    @Json(name = "members") val members: Members,
    @Json(name = "plan") val plan: Plan
)

@Keep
data class Plan(
    @Json(name = "memberLimit") val memberLimit: Int,
    @Json(name = "supporterLimit") val supporterLimit: Int
)

@Keep
data class Members(
    @Json(name = "total") val total: Int,
    @Json(name = "administrators") val administrators: Int,
    @Json(name = "managers") val managers: Int,
    @Json(name = "editors") val editors: Int,
    @Json(name = "members") val members: Int,
    @Json(name = "supporters") val supporters: Int
)