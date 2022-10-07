package com.appsoft.splyza.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appsoft.splyza.data.model.InviteTeamRequest
import com.appsoft.splyza.data.model.InviteTeamResponse
import com.appsoft.splyza.data.model.TeamResponse
import com.appsoft.splyza.data.network.ResourceState
import com.appsoft.splyza.data.repository.TeamRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val teamRepo: TeamRepository
) : ViewModel() {

    var inviteUrl: String? = ""
    var teamId = ""

    private val _teamLiveData by lazy {
        MutableLiveData<ResourceState<TeamResponse>>()
    }
    val teamLiveData: LiveData<ResourceState<TeamResponse>> = _teamLiveData

    private val _inviteUrlLiveData by lazy {
        MutableLiveData<ResourceState<InviteTeamResponse>>()
    }
    val inviteUrlLiveData: LiveData<ResourceState<InviteTeamResponse>> = _inviteUrlLiveData

    fun getTeam(teamId: String) {
        viewModelScope.launch {
            val team = teamRepo.getTeam(teamId)
            _teamLiveData.postValue(team)
        }
    }

    fun updateRole(teamId: String, permissionLevel: String) {
        val role:String = when(permissionLevel) {
            "Coach" -> "manager"
            "Player Coach" -> "editor"
            "Player" -> "member"
            "Supporter" -> "readonly"
            else -> {"Invalid Role"}
        }
        viewModelScope.launch {
            val inviteUrl = teamRepo.getTeamInviteUrl(teamId, InviteTeamRequest(role))
            _inviteUrlLiveData.postValue(inviteUrl)
        }
    }
}