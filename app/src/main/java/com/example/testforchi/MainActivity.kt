package com.example.testforchi

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.testforchi.AnimalPagerAdapter
import com.example.testforchi.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.navigation.NavigationView
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {



    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView


    private lateinit var textViewCounter: TextView
    private lateinit var buttonIncrement: Button
    private var counter = 0

    companion object {
        private const val REQUEST_CODE_COUNTER_FRAGMENT = 1001
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        
        val viewPager: ViewPager2 = findViewById(R.id.viewPager)
        val tabLayout: TabLayout = findViewById(R.id.tabLayout)

        val pagerAdapter = AnimalPagerAdapter(supportFragmentManager, lifecycle)
        viewPager.adapter = pagerAdapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Animals"
                else -> "Favorites"
            }
        }.attach()
    }
}


        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigation_view)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)

        if (savedInstanceState == null) {
            replaceFragment(Fragment1())
            navigationView.setCheckedItem(R.id.menu_add_user)
        }

        setupDrawer()
    }


    private fun setupDrawer() {
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_add_user -> {
                    openAddUserActivity()
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                else -> false
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START)
                } else {
                    drawerLayout.openDrawer(GravityCompat.START)
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun openAddUserActivity() {
        val intent = Intent(this, AddUserActivity::class.java)
        startActivityForResult(intent, REQUEST_CODE_ADD_USER)
    }

    companion object {
        const val REQUEST_CODE_ADD_USER = 1001
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_ADD_USER && resultCode == Activity.RESULT_OK) {
            // Notify Fragment1 that a user is added successfully
            val fragment1 = supportFragmentManager.findFragmentById(R.id.fragment_container)
            if (fragment1 is Fragment1) {
                fragment1.refreshUserList()
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        if (navHostFragment != null) {
            val currentFragment = navHostFragment.childFragmentManager.primaryNavigationFragment
            if (currentFragment is UserDetailsFragment) {
                // If the current fragment is UserDetailsFragment, navigate back
                val navController = currentFragment.findNavController()
                navController.navigateUp()
            } else {
                super.onBackPressed()
            }
        } else {
            super.onBackPressed()
        }
    }
}


        textViewCounter = findViewById(R.id.tvCounterActivity)
        buttonIncrement = findViewById(R.id.btnIncrement)

        buttonIncrement.setOnClickListener {
            counter++
            textViewCounter.text = counter.toString()
        }

        buttonIncrement.setOnClickListener {
            val intent = Intent(this, FragmentHostActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_COUNTER_FRAGMENT)
        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_COUNTER_FRAGMENT && resultCode == Activity.RESULT_OK) {
            val counterFromFragment = data?.getIntExtra("counter", 0) ?: 0
            counter = counterFromFragment
            textViewCounter.text = counter.toString()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("counter", counter)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        counter = savedInstanceState.getInt("counter", 0)
        textViewCounter.text = counter.toString()
    }



    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val fragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainer)
        if (fragment is CounterFragment) {
            val fragmentCounter = fragment as CounterFragment
            textViewCounter.text = fragmentCounter.getCounter().toString()
        }
        super.onBackPressed()
    }

}


