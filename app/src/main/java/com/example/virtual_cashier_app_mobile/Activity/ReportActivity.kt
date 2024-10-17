package com.example.virtual_cashier_app_mobile.Activity

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.virtual_cashier_app_mobile.Adapter.ReportListAdapter
import com.example.virtual_cashier_app_mobile.R
import com.example.virtual_cashier_app_mobile.ViewModel.MainViewModel
import com.example.virtual_cashier_app_mobile.databinding.ActivityReportBinding

class ReportActivity : AppCompatActivity() {
    lateinit var binding: ActivityReportBinding
    private val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityReportBinding.inflate(layoutInflater)
        setContentView(binding.root)


        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        initRecyclerview()
        setVariable()
    }

    private fun setVariable() {
        binding.backBtn.setOnClickListener { finish() }
    }

    private fun initRecyclerview() {
        binding.view2.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false)
        binding.view2.adapter = ReportListAdapter(mainViewModel.loadBudget())
        binding.view2.isNestedScrollingEnabled = false
    }
}