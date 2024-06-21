//package com.capstone.signlingo
//
//
//import android.content.Intent
//import android.os.Bundle
//import android.widget.TextView
//import androidx.appcompat.app.AppCompatActivity
//import com.capstone.signlingo.databinding.ActivityProfileBinding
//import com.google.android.ads.mediationtestsuite.activities.HomeActivity
//import com.google.android.material.bottomnavigation.BottomNavigationView
//
//class ProfileActivity : AppCompatActivity() {
//
//    private lateinit var binding: ActivityProfileBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityProfileBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        binding.settingsIcon.setOnClickListener {
//            // Handle settings icon click
//        }
//
//        binding.editProfileButton.setOnClickListener {
//            // Handle edit profile button click
//        }
//
//        binding.logout.setOnClickListener {
//            // Handle log out button click
//        }
//
//        val bottomNavigationView: BottomNavigationView = binding.bottomNavigation
//        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
//            when (item.itemId) {
//                R.id.nav_home -> {
//                    startActivity(Intent(this, HomeActivity::class.java))
//                    true
//                }
//                R.id.nav_scan -> {
//                    startActivity(Intent(this, ScanActivity::class.java))
//                    true
//                }
//                R.id.nav_dictionary -> {
//                    startActivity(Intent(this, DictionaryActivity::class.java))
//                    true
//                }
//                R.id.nav_profile -> {
//                    // Current activity, do nothing
//                    true
//                }
//                else -> false
//            }
//        }
//    }
//}
