package com.appsoft.splyza.data.model

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class InviteTeamResponse(@Json(name = "url") val inviteUrl: String)
