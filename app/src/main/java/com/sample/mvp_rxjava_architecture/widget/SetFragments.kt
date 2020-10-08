package com.sample.mvp_rxjava_architecture.widget

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class SetFragments {

    var activity: FragmentActivity? = null
    var viewPager2: ViewPager2? = null
    var tabLayout: TabLayout? = null
    var fragments: Array<Fragment>? = null

    /*依fragments順序*/
    var tabs: ArrayList<Int>? = null

    companion object {

        fun newInstance(
            activity: FragmentActivity,
            vp_layout: ViewPager2,
            tab_layout: TabLayout,
            fragments: Array<Fragment>,
            tabs: ArrayList<Int>
        ): SetFragments {
            val setFragments = SetFragments()
            setFragments.activity = activity
            setFragments.viewPager2 = vp_layout
            setFragments.tabLayout = tab_layout
            setFragments.fragments = fragments
            setFragments.tabs = tabs
            setFragments.create()
            return setFragments
        }

    }


    class Builder(activity: FragmentActivity) {

        private var activity: FragmentActivity? = activity
        private var viewPager2: ViewPager2? = null
        private var tabLayout: TabLayout? = null
        private var fragments: Array<Fragment>? = null
        private var tabs: ArrayList<Int>? = null

        fun setViewPager(viewPager2: ViewPager2) = apply { this.viewPager2 = viewPager2 }

        fun setTabLayout(tabLayout: TabLayout) = apply { this.tabLayout = tabLayout }

        fun fragments(fragments: Array<Fragment>) = apply { this.fragments = fragments }

        fun tabs(tabs: ArrayList<Int>) = apply { this.tabs = tabs }

        fun build() = newInstance(activity!!, viewPager2!!, tabLayout!!, fragments!!, tabs!!)

    }


    fun create() {

        viewPager2?.adapter = object : FragmentStateAdapter(activity!!) {
            override fun getItemCount(): Int {
                return fragments!!.size
            }

            override fun createFragment(position: Int): Fragment {
                return fragments?.get(position)!!
            }
        }

        TabLayoutMediator(tabLayout!!, viewPager2!!) { tab, position ->
            tab.text = activity!!.getText(tabs?.get(position)!!)
        }.attach()

    }


}