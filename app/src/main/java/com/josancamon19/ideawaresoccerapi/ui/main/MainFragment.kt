package com.josancamon19.ideawaresoccerapi.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.josancamon19.ideawaresoccerapi.adapters.pager.MatchesPagerAdapter
import com.josancamon19.ideawaresoccerapi.databinding.FragmentMainBinding
import com.josancamon19.ideawaresoccerapi.R
import dagger.android.support.DaggerFragment


class MainFragment : DaggerFragment() {

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        setupViewPager()
        return binding.root
    }

    private fun setupViewPager() {
        val pagerAdapter = MatchesPagerAdapter(fragmentManager!!)
        binding.viewPager.adapter = pagerAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }

}