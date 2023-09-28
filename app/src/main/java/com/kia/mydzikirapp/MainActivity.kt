package com.kia.mydzikirapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.viewpager2.widget.ViewPager2
import com.kia.mydzikirapp.adapter.ArtikelAdapter
import com.kia.mydzikirapp.model.Artikel
import com.kia.mydzikirapp.ui.HarianDzikrDoaActivity
import com.kia.mydzikirapp.ui.PagiPetangDzikirActivity
import com.kia.mydzikirapp.ui.QauliyahShalatActivity
import com.kia.mydzikirapp.ui.SetiapSaatDzikrActivity

class MainActivity : AppCompatActivity() {

    private val listArtikel: ArrayList<Artikel> = arrayListOf()
    private lateinit var dotSlider: Array<ImageView?>
    private val slidingCallback = object : ViewPager2.OnPageChangeCallback(){
        override fun onPageSelected(position: Int) {
            for (i in 0 until listArtikel.size){
                dotSlider[i]?.setImageDrawable(
                    ContextCompat.getDrawable(applicationContext, R.drawable.dot_inactive)
                )
            }

            dotSlider[position]?.setImageDrawable(
                ContextCompat.getDrawable(applicationContext, R.drawable.dot_active)
            )
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_MyDzikirAppThemes)
        installSplashScreen()
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initData()
        initView()
        setupViewPager()
    }

    private fun setupViewPager() {
        val llSliderDots: LinearLayout =  findViewById(R.id.ll_slider_dots)
        dotSlider = arrayOfNulls(listArtikel.size)
        for (i in 0 until listArtikel.size){
            dotSlider[i] = ImageView(this)
            dotSlider[i]?.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext, R.drawable.dot_inactive
                )
            )

//            menentukan lebar dan tinggi indikator
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
//            mengatur jarak antar indikator
            params.setMargins(9,0,8,0)
//            mengatur posisi indicator
            params.gravity = Gravity.CENTER_VERTICAL

            llSliderDots.addView(dotSlider[i], params)
        }

        dotSlider[0]?.setImageDrawable(
            ContextCompat.getDrawable(
                applicationContext, R.drawable.dot_active
            )
        )
    }

    private fun initData() {
        val titleArtikel = resources.getStringArray(R.array.arr_title_artikel)
        val descArtikel = resources.getStringArray(R.array.arr_desc_artikel)
        val imageArtikel = resources.obtainTypedArray(R.array.arr_img_artikel)

        for (data in titleArtikel.indices){
            val artikel = Artikel(
                titleArtikel[data],
                descArtikel[data],
                imageArtikel.getResourceId(data, 0)
            )
            listArtikel.add(artikel)
        }
        imageArtikel.recycle()
    }

    private fun initView() {
        val llDzikirDoaShalat: LinearLayout = findViewById(R.id.ll_dzikir_doa_sholat)
        llDzikirDoaShalat.setOnClickListener {
            startActivity(Intent(this, QauliyahShalatActivity::class.java))
        }
        val llDzikirSetiapSaat: LinearLayout = findViewById(R.id.ll_dzikir_setiap_saat)
       llDzikirSetiapSaat.setOnClickListener {
           startActivity(Intent(this, SetiapSaatDzikrActivity::class.java))
       }
        val llDzikirDoaHarian: LinearLayout = findViewById(R.id.ll_dzikir_doa_harian)
        llDzikirDoaHarian.setOnClickListener {
            startActivity(Intent(this, HarianDzikrDoaActivity::class.java))
        }
        val llDzikirPagiPetang: LinearLayout = findViewById(R.id.ll_dzikir_pagi_sore)
        llDzikirPagiPetang.setOnClickListener {
            startActivity(Intent(this, PagiPetangDzikirActivity::class.java))
        }
        val vpArtikel: ViewPager2 = findViewById(R.id.vp_artikel)
        vpArtikel.adapter = ArtikelAdapter(listArtikel)
        vpArtikel.registerOnPageChangeCallback(slidingCallback)
    }
}