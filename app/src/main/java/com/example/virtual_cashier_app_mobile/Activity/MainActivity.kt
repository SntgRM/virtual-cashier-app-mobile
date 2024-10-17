package com.example.virtual_cashier_app_mobile.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.virtual_cashier_app_mobile.Adapter.ExpenseListAdapter
import com.example.virtual_cashier_app_mobile.ViewModel.MainViewModel
import com.example.virtual_cashier_app_mobile.databinding.ActivityMainBinding
import eightbitlab.com.blurview.RenderScriptBlur

class MainActivity : AppCompatActivity() {
    lateinit var  binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels ()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        initRecyclerview()
        setBlueEffect()
        setVariable()


    }

    private fun setVariable() {
        binding.cardBtn.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    ReportActivity::class.java
                )
            )
        }
    }

    private fun setBlueEffect() {
        val radius=10f
        val decorView=this.window.decorView
        val rootView=decorView.findViewById<View>(android.R.id.content) as ViewGroup
        val windowBackGround=decorView.background
        binding.blueView.setupWith(
            rootView,
            RenderScriptBlur(this)

        )

            .setFrameClearDrawable(windowBackGround)
            .setBlurRadius(radius)

        binding.blueView.setOutlineProvider(ViewOutlineProvider.BACKGROUND)
        binding.blueView.setClipToOutline(true)
    }

    private fun initRecyclerview() {
        binding.view1.layoutManager=LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.view1.adapter=ExpenseListAdapter(mainViewModel.loadData())
        binding.view1.isNestedScrollingEnabled=false
    }
}