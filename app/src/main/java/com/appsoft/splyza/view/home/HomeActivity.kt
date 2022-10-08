package com.appsoft.splyza.view.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.appsoft.splyza.R
import com.appsoft.splyza.base.extensions.changeFragment
import com.appsoft.splyza.base.extensions.setVisibility
import com.appsoft.splyza.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        changeFragment(
            containerViewId = R.id.fragmentContainer,
            newFragment = HomeFragment.newInstance(),
            replaceFragment = true
        )
    }
}