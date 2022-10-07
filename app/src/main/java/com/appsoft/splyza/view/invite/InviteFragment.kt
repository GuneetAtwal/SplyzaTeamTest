package com.appsoft.splyza.view.invite

import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.appsoft.splyza.R
import com.appsoft.splyza.base.extensions.*
import com.appsoft.splyza.data.model.TeamResponse
import com.appsoft.splyza.data.network.ResourceState
import com.appsoft.splyza.databinding.DialogPermissionsLevelsBinding
import com.appsoft.splyza.databinding.FragmentInviteBinding
import com.appsoft.splyza.view.qr.QrFragment
import com.appsoft.splyza.viewmodel.HomeViewModel
import com.appsoft.splyza.viewmodel.InviteViewModel

class InviteFragment: Fragment() {

    private val homeViewModel: HomeViewModel by activityViewModels()
    private lateinit var binding: FragmentInviteBinding
    private lateinit var viewModel: InviteViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInviteBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(InviteViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (::binding.isInitialized) {
            setupHeader()
            getInitData()
            observeData()
            setClickListeners()
        }
    }

    private fun getInitData() {
        homeViewModel.getTeam(homeViewModel.teamId)
        homeViewModel.updateRole(homeViewModel.teamId, binding.tvCurrentPermission.text.toString())
    }

    private fun observeData() {
        homeViewModel.teamLiveData.observe(viewLifecycleOwner) {
            when(it) {
                is ResourceState.Success -> {
                    it.body.let { team ->

                        viewModel.members = team.members.total - team.members.supporters
                        viewModel.membersLimit = team.plan.memberLimit
                        viewModel.supporters = team.members.supporters
                        viewModel.supportersLimit = team.plan.supporterLimit
                    }
                    setupUI(it.body)
                }

                is ResourceState.Failure -> {

                }
            }
        }

        homeViewModel.inviteUrlLiveData.observe(viewLifecycleOwner) {
            when(it) {
                is ResourceState.Success -> {
                    homeViewModel.inviteUrl = it.body.inviteUrl
                }

                is ResourceState.Failure -> {

                }
            }
        }
    }

    private fun setClickListeners() {
        binding.apply {
            btnCopyLink.setOnClickListener {
                copyLinkToClipBoard()
            }

            btnShareQr.setOnClickListener {
                activity?.changeFragment(
                    containerViewId = R.id.fragmentContainer,
                    newFragment = QrFragment.newInstance(),
                    replaceFragment = false,
                    addToBackStack = true
                )
            }

            rlPermLevels.setOnClickListener {
                showPermissionsDialog()
            }
        }
    }

    private fun setupHeader() {
        binding.customToolbar.apply {
            tvBack.setOnClickListener {
                activity?.onBackPressed()
            }
            tvTitle.text = getString(R.string.invite_members)
        }
    }

    private fun setupUI(team: TeamResponse) {
        binding.apply {
            binding.groupSupporters.setVisibility(team.plan.supporterLimit > 0)
            tvCurrentMembersNum.text = (team.members.total - team.members.supporters).toString()
            tvCurrentSupportersNum.text = team.members.supporters.toString()
            tvMembersLimitNum.text = team.plan.memberLimit.toString()
            tvSupportersLimitNum.text = team.plan.supporterLimit.toString()
        }
    }

    private fun copyLinkToClipBoard() {
        val clipboardManager = activity?.applicationContext?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText(
            COPY_LABEL,
            homeViewModel.inviteUrl ?: ""
        )
        clipboardManager.setPrimaryClip(clipData)
        activity?.showShortToast(getString(R.string.copied))
    }

    private fun showPermissionsDialog() {
        val dialogBinding = DialogPermissionsLevelsBinding.inflate(
            LayoutInflater.from(binding.root.context)
        )

        val dialog = Dialog(requireActivity(), R.style.Theme_Dialog).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(true)
            setContentView(dialogBinding.root)
            window?.apply {
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
        }

        binding.apply {
            dialogBinding.tvCoach.setOnClickListener {
                tvCurrentPermission.text = getString(R.string.coach)
                homeViewModel.updateRole(homeViewModel.teamId, tvCurrentPermission.text.toString())
                dialog.dismiss()
            }

            dialogBinding.tvPlayerCoach.setOnClickListener {
                tvCurrentPermission.text = getString(R.string.player_coach)
                homeViewModel.updateRole(homeViewModel.teamId, tvCurrentPermission.text.toString())
                dialog.dismiss()
            }

            dialogBinding.tvPlayer.setOnClickListener {
                tvCurrentPermission.text = getString(R.string.player)
                homeViewModel.updateRole(homeViewModel.teamId, tvCurrentPermission.text.toString())
                dialog.dismiss()
            }

            dialogBinding.tvSupporter.setOnClickListener {
                tvCurrentPermission.text = getString(R.string.supporter)
                homeViewModel.updateRole(homeViewModel.teamId, tvCurrentPermission.text.toString())
                dialog.dismiss()
            }
        }



        dialogBinding.apply {
            /**
             * If there are no available member slots (the team is full),
             * the Coach, Player Coach, and
             * Player options should be disabled.
             * **/
            if (viewModel.members == viewModel.membersLimit) {
                tvCoach.isEnabled = false
                tvPlayer.isEnabled = false
                tvPlayerCoach.isEnabled = false
                tvCoach.setTextColor(root.context.getColorFromRes(R.color.light_gray))
                tvPlayer.setTextColor(root.context.getColorFromRes(R.color.light_gray))
                tvPlayerCoach.setTextColor(root.context.getColorFromRes(R.color.light_gray))
            } else {
                tvCoach.isEnabled = true
                tvPlayer.isEnabled = true
                tvPlayerCoach.isEnabled = true
                tvCoach.setTextColor(root.context.getColorFromRes(R.color.azure_blue))
                tvPlayer.setTextColor(root.context.getColorFromRes(R.color.azure_blue))
                tvPlayerCoach.setTextColor(root.context.getColorFromRes(R.color.azure_blue))
            }

            /**
             * If Supporters are not available for the Team (the Supporter limit is 0),
             * then the Supporter options should be hidden.
             **/
            if (viewModel.supportersLimit == 0) {
                tvSupporter.setVisibility(false)
            } else {
                /**
                 * If Supporters are available but there are no open slots,
                 * the Supporter option should be disabled
                 * **/
                if (viewModel.supporters > 0 && viewModel.supporters == viewModel.supportersLimit) {
                    tvSupporter.isEnabled = false
                    tvSupporter.setTextColor(root.context.getColorFromRes(R.color.light_gray))
                } else {
                    tvSupporter.setVisibility(true)
                    tvSupporter.setTextColor(root.context.getColorFromRes(R.color.azure_blue))
                }
            }
        }

        dialog.show()
    }


    companion object {
        private val TAG: String = InviteFragment::class.java.simpleName
        private const val COPY_LABEL = "Splyza_Team_Invite_Link"
        fun newInstance() =  InviteFragment()
    }
}