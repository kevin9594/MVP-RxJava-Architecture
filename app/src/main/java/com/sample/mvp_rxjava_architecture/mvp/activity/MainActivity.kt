package com.sample.mvp_rxjava_architecture.mvp.activity

import android.os.Bundle
import com.sample.mvp_rxjava_architecture.R
import com.sample.mvp_rxjava_architecture.base.BaseActivity
import com.sample.mvp_rxjava_architecture.mvp.fragment.CityListFragment
import com.sample.mvp_rxjava_architecture.mvp.fragment.SignInFragment
import com.sample.mvp_rxjava_architecture.widget.SetFragments
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setToolBar(R.string.title)
        setFragments()
    }

    private fun setFragments(){

        SetFragments.Builder(this)
            .setViewPager(vp_main)
            .setTabLayout(tab_main)
            .fragments(arrayOf(SignInFragment(), CityListFragment()))
            .tabs(arrayListOf(SignInFragment.title, CityListFragment.title))
            .build()

    }

}