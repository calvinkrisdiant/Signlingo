package com.capstone.signlingo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import me.relex.circleindicator.CircleIndicator3

class OnboardingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        val viewPager: ViewPager2 = findViewById(R.id.viewPager)
        val indicator: CircleIndicator3 = findViewById(R.id.indicator)
        val getStartedButton: Button = findViewById(R.id.getStartedButton)

        val fragments = listOf(
            OnboardingFragment.newInstance(
                "SignLingo",
                "Bahasa isyarat adalah jembatan untuk komunikasi tanpa batas.",
                R.drawable.ic_logo_signlingo
            ),
            OnboardingFragment.newInstance(
                "SignLingo",
                "Mari Mulai! Bersama-sama, kita dapat memahami dan berkomunikasi lebih baik.",
                R.drawable.ic_logo_signlingo
            )
        )

        val adapter = OnboardingAdapter(this, fragments)
        viewPager.adapter = adapter
        indicator.setViewPager(viewPager)

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                getStartedButton.isEnabled = (position == fragments.size - 1)
                if (getStartedButton.isEnabled) {
                    getStartedButton.setBackgroundResource(R.drawable.button_background_enabled)
                    getStartedButton.setOnClickListener {
                        startActivity(Intent(this@OnboardingActivity, WelcomeActivity::class.java))
                    }
                } else {
                    getStartedButton.setBackgroundResource(R.drawable.button_background_disabled)
                    getStartedButton.setOnClickListener(null)
                }
            }
        })
    }
}
