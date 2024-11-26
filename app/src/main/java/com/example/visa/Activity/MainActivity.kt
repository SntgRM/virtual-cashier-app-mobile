package com.example.visa.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.view.ViewOutlineProvider
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.visa.Adapter.ExpenseListAdapter
import com.visa.R
import com.example.visa.ViewModel.MainViewModel
import com.visa.databinding.ActivityMainBinding
import eightbitlab.com.blurview.RenderScriptBlur

class MainActivity : AppCompatActivity() {
    lateinit var  binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels ()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = resources.getColor(R.color.dark_blue)

        initRecyclerview()
        setBlueEffect()
        setVariable()
        setupLogout()
        setupMenu()

        val phoneNumber = intent.getStringExtra("phone_number")
        val userName = intent.getStringExtra("user_name")

        if (phoneNumber != null) {
            val formattedPhoneNumber = "+57 $phoneNumber"
            binding.phoneNumber.text = formattedPhoneNumber

            val greetingMessage = "Hola, $userName !"
            binding.textView.text = greetingMessage
        }
    }

    private fun setupMenu() {
        val menuButton = findViewById<LinearLayout>(R.id.profile_menu)
        menuButton.setOnClickListener { view ->
            val popupMenu = PopupMenu(this, view)
            popupMenu.menuInflater.inflate(R.menu.logout_menu, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.logout -> {
                        val intent = Intent(this, IntroActivity::class.java)
                        startActivity(intent)
                        finish()
                        true
                    }
                    else -> false
                }
            }

            try {
                val fieldMPopup = PopupMenu::class.java.getDeclaredField("mPopup")
                fieldMPopup.isAccessible = true
                val mPopup = fieldMPopup.get(popupMenu)
                mPopup.javaClass
                    .getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                    .invoke(mPopup, true)
            } catch (e:Exception){
                Log.e("Main", "Error al mostrar el icono de Logout")
            } finally {
                popupMenu.show()
            }
        }
    }


    private fun setVariable() {
        binding.cardBtn.setOnClickListener {
            startActivity(
                Intent(this, ReportActivity::class.java)
            )
        }
    }

    private fun setupLogout() {
        binding.logoutBtn.setOnClickListener {
            val intent = Intent(this, IntroActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun setBlueEffect() {
        val radius=10f
        val decorView=this.window.decorView
        val rootView=decorView.findViewById<View>(android.R.id.content) as ViewGroup
        val windowBackGround=decorView.background
        binding.blurView.setupWith(
            rootView,
            RenderScriptBlur(this)

        )

            .setFrameClearDrawable(windowBackGround)
            .setBlurRadius(radius)

        binding.blurView.setOutlineProvider(ViewOutlineProvider.BACKGROUND)
        binding.blurView.setClipToOutline(true)
    }

    private fun initRecyclerview() {
        binding.view1.layoutManager=LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.view1.adapter=ExpenseListAdapter(mainViewModel.loadData())
        binding.view1.isNestedScrollingEnabled=false
    }
}