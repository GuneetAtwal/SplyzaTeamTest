package com.appsoft.splyza.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.appsoft.splyza.R
import com.appsoft.splyza.base.extensions.setVisibility
import com.appsoft.splyza.databinding.FragmentHomeBinding

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
            setupUI()
        }
    }

    private fun setOnClickListeners() {
        binding.btnInvite.setOnClickListener {
            //Go To Invitation View
        }
    }

    private fun setupUI() {
        if (activity is HomeActivity) {
            (activity as HomeActivity).supportActionBar?.apply {
                customView.apply {
                    findViewById<TextView>(R.id.tvBack).setVisibility(false)
                    findViewById<TextView>(R.id.tvTitle).text = getString(R.string.invite_now)
                }
            }
        }
    }
}