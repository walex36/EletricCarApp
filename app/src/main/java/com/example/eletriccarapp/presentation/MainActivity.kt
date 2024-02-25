package com.example.eletriccarapp.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.eletriccarapp.R
import com.example.eletriccarapp.presentation.adapters.TabAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    private lateinit var ibtnCalculate: FloatingActionButton
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPage: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpView()
        setUpTabs()
        setListeners()
    }

    private fun setUpView() {
        tabLayout = findViewById(R.id.tab_layout)
        viewPage = findViewById(R.id.vp_viewPager)
        ibtnCalculate = findViewById(R.id.ib_calculate)
    }

    private fun setUpTabs() {

        val tabsAdapter = TabAdapter(this)
        viewPage.adapter = tabsAdapter
    }

    private fun setListeners() {
        ibtnCalculate.setOnClickListener {
            val intent = Intent(this, CalculatePageActivity::class.java)
            startActivity(intent)
        }
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    viewPage.currentItem = it.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        viewPage.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.getTabAt(position)?.select()
            }
        })
    }


}