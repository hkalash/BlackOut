package com.smartsoftwaresolutions.blackout

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.smartsoftwaresolutions.blackout.databinding.ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity() {
    private lateinit var binding :ActivitySplashScreenBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // remove the toolbar
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        // set the binding
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        val view =binding.root

        setContentView(view)


        //set animation
        animate()

        Handler().postDelayed(
            {

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            },1200)
    }

    private fun animate() {
     //   binding.SplashScreenImage.animate().alpha(1f).duration = 900 // for the image
        binding.llLogo.animate().alpha(1f).duration = 700 // for the text
        val animation = ObjectAnimator.ofFloat(binding.llLogo, "translationY", 500f)
        animation.duration = 700
        animation.start()
    }


}