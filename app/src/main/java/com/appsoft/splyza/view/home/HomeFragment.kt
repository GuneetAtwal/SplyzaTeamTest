package com.appsoft.splyza.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.appsoft.splyza.R
import com.appsoft.splyza.base.extensions.changeFragment
import com.appsoft.splyza.base.extensions.setVisibility
import com.appsoft.splyza.databinding.FragmentHomeBinding
import com.appsoft.splyza.view.invite.InviteFragment

class HomeFragment: Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (::binding.isInitialized) {
            setOnClickListeners()
            setupHeader()
        }
    }

    private fun setOnClickListeners() {
        binding.btnInvite.setOnClickListener {
            activity?.changeFragment(
                containerViewId = R.id.fragmentContainer,
                newFragment = InviteFragment.newInstance(),
                replaceFragment = false,
                addToBackStack = true
            )
        }
    }

    private fun setupHeader() {
        binding.customToolbar.apply {
            tvBack.setVisibility(false)
            tvTitle.text = getString(R.string.invite_now)
        }
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}