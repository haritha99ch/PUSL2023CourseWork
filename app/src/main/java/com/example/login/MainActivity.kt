package com.example.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.login.Data.GGDbContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.abs

class MainActivity : AppCompatActivity() {

    //newly addded to create the image slider
   private lateinit var viewPager2: ViewPager2
    private val sliderHandler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Database
        CoroutineScope(Dispatchers.IO).launch {
            val Dao= GGDbContext.getInstance(applicationContext).gGdbDao
            val posts=Dao.selectPostswAccount()
        }

        //button
        val  button = findViewById<Button>(R.id.loginbtn)

        button.setOnClickListener {
            val intent = Intent(this,login::class.java)
            startActivity(intent)
            finish()
        }

        //new code for image slideshow
        viewPager2 = findViewById(R.id.viewPager_ImageSlider)

        val sliderItems:MutableList<Slideritem> = ArrayList()
        sliderItems.add(Slideritem(R.drawable.assainn))
        sliderItems.add(Slideritem(R.drawable.pubg))
        sliderItems.add(Slideritem(R.drawable.killer))
        sliderItems.add(Slideritem(R.drawable.rainboow))
        sliderItems.add(Slideritem(R.drawable.valo))

        viewPager2.adapter = SliderAdapter(sliderItems,viewPager2)

        viewPager2.clipToPadding = false
        viewPager2.clipChildren =false
        viewPager2.offscreenPageLimit = 3
        viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(30))
        compositePageTransformer.addTransformer{page,position ->
            val r =1- abs(position)
            page.scaleY = 0.85f + r * 0.25f
        }

        viewPager2.setPageTransformer(compositePageTransformer)

        viewPager2.registerOnPageChangeCallback(object:ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                sliderHandler.removeCallbacks(sliderRunnable)
                sliderHandler.postDelayed(sliderRunnable, 3000)
            }
        })

    }

private val sliderRunnable = Runnable {
    viewPager2.currentItem = viewPager2.currentItem+1
}

override fun onPause() {
    super.onPause()
    sliderHandler.postDelayed(sliderRunnable,3000)
}

override fun onResume() {
    super.onResume()
    sliderHandler.postDelayed(sliderRunnable,3000)
}
}
