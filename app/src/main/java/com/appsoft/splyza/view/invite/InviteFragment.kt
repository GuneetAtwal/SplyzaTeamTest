package com.appsoft.splyza.view.invite

import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.appsoft.splyza.BuildConfig
import com.appsoft.splyza.R
import com.appsoft.splyza.base.extensions.getColorFromRes
import com.appsoft.splyza.base.extensions.setVisibility
import com.appsoft.splyza.data.model.TeamResponse
import com.appsoft.splyza.data.network.ResourceState
import com.appsoft.splyza.databinding.DialogPermissionsLevelsBinding
import com.appsoft.splyza.databinding.FragmentInviteBinding
import com.appsoft.splyza.view.home.HomeActivity
import com.appsoft.splyza.viewmodel.HomeViewModel

class InviteFragment: Fragment() {
    private lateinit var binding: FragmentInviteBinding
    private val viewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInviteBinding.inflate(inflater, container, false)
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
        viewModel.getTeam(viewModel.teamId)
    }

    private fun observeData() {
        viewModel.teamLiveData.observe(viewLifecycleOwner) {
            when(it) {
                is ResourceState.Success -> {
                    it.body.let { team ->
                        val members = team.members.total - team.members.supporters
                        if (members == team.plan.memberLimit) {
                            viewModel.isTeamFull = true
                        }
                        viewModel.supporters = team.members.supporters
                        viewModel.supportersLimit = team.plan.supporterLimit
                    }
                    setupUI(it.body)
                }

                is ResourceState.Failure -> {

                }
            }
        }

        viewModel.inviteUrlLiveData.observe(viewLifecycleOwner) {
            when(it) {
                is ResourceState.Success -> {
                    viewModel.inviteUrl = it.body.inviteUrl
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
                //Go to QrFragment
            }

            rlPermLevels.setOnClickListener {
                showPermissionsDialog()
            }
        }
    }

    private fun setupHeader() {
        if (activity is HomeActivity) {
            (activity as HomeActivity).supportActionBar?.apply {
                customView.apply {
                    findViewById<TextView>(R.id.tvBack).setVisibility(true)
                    findViewById<TextView>(R.id.tvTitle).text = getString(R.string.invite_members)
                    findViewById<TextView>(R.id.tvBack).setOnClickListener {
                        it.setVisibility(false)
                    }
                }
            }
        }
    }

    private fun setupUI(team: TeamResponse) {
        binding.apply {
            if (team.plan.supporterLimit < 1) {
                binding.groupSupporters.visibility = View.GONE
            }
            tvCurrentMembersNum.text = team.members.total.toString()
            tvCurrentSupportersNum.text = team.members.supporters.toString()
            tvMembersLimitNum.text = team.plan.memberLimit.toString()
            tvSupportersLimitNum.text = team.plan.supporterLimit.toString()
        }
    }

    private fun copyLinkToClipBoard() {
        val clipboardManager = activity?.applicationContext?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("Splyza_Team_Invite_Link", viewModel.inviteUrl)
        clipboardManager.setPrimaryClip(clipData)
        if (BuildConfig.DEBUG) {
            Log.d(TAG,"Copied Text: ${clipboardManager.primaryClip?.getItemAt(0)?.text}")
        }
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

        dialogBinding.tvCoach.setOnClickListener {
            binding.tvCurrentPermLevel.text = getString(R.string.coach)
            viewModel.updateRole(viewModel.teamId, binding.tvCurrentPermLevel.text.toString())
            dialog.dismiss()
        }

        dialogBinding.tvPlayerCoach.setOnClickListener {
            binding.tvCurrentPermLevel.text = getString(R.string.player)
            viewModel.updateRole(viewModel.teamId, binding.tvCurrentPermLevel.text.toString())
            dialog.dismiss()
        }

        dialogBinding.tvPlayer.setOnClickListener {
            binding.tvCurrentPermLevel.text = getString(R.string.player)
            viewModel.updateRole(viewModel.teamId, binding.tvCurrentPermLevel.text.toString())
            dialog.dismiss()
        }

        dialogBinding.tvSupporter.setOnClickListener {
            binding.tvCurrentPermLevel.text = getString(R.string.supporter)
            viewModel.updateRole(viewModel.teamId, binding.tvCurrentPermLevel.text.toString())
            dialog.dismiss()
        }

        dialogBinding.apply {
            /** Team is full **/
            if (viewModel.isTeamFull) {
                tvCoach.isEnabled = false
                tvPlayer.isEnabled = false
                tvPlayerCoach.isEnabled = false
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

            /** no supporters **/
            if (viewModel.supportersLimit == 0) {
                tvSupporter.setVisibility(false)
            } else {
                /** No slots left **/
                if (viewModel.supporters > 0 && viewModel.supporters == viewModel.supportersLimit) {
                    tvSupporter.isEnabled = false
                    tvSupporter.setTextColor(root.context.getColorFromRes(R.color.light_gray))
                } else {
                    tvSupporter.setVisibility(true)
                    tvSupporter.setTextColor(root.context.getColorFromRes(android.R.color.holo_blue_dark))
                }
            }
        }

        dialog.show()
    }

    companion object {
        private val TAG: String = InviteFragment::class.java.simpleName
        fun newInstance() =  InviteFragment()
    }
}