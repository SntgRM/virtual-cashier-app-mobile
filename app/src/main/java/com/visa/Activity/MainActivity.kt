package com.visa.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.view.ViewOutlineProvider
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.visa.Adapter.ExpenseListAdapter
import com.visa.R
import com.visa.ViewModel.MainViewModel
import com.visa.databinding.ActivityMainBinding
import eightbitlab.com.blurview.RenderScriptBlur

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels ()
    private var currentAmount: Double = 3546.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = resources.getColor(R.color.dark_blue)

        initRecyclerview()
        setBlueEffect()
        setVariable()
        setupMenu()

        val phoneNumber = intent.getStringExtra("phone_number")
        val userName = intent.getStringExtra("user_name")
        val amount: TextView = findViewById(R.id.amount)
        val sendButton: LinearLayout = findViewById(R.id.sendButton)
        val withdrawButton: LinearLayout = findViewById(R.id.withdrawButton)
        val depositButton: LinearLayout = findViewById(R.id.depositButton)

        if (phoneNumber != null) {
            val formattedPhoneNumber = "+57 $phoneNumber"
            binding.phoneNumber.text = formattedPhoneNumber

            val greetingMessage = "Hola, $userName !"
            binding.textView.text = greetingMessage
        }

        updateAmount(amount)

        sendButton.setOnClickListener { showSendDialog(amount) }
        withdrawButton.setOnClickListener { showWithdrawDialog(amount) }
        depositButton.setOnClickListener { showDepositDialog(amount) }
    }


    private fun showSendDialog(amount: TextView) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_send, null)
        val dialog = android.app.AlertDialog.Builder(this)
            .setTitle("Transferir Dinero")
            .setView(dialogView)
            .setPositiveButton("Transferir") { _, _ ->
                val amountToTransfer = dialogView.findViewById<EditText>(R.id.amount).text.toString().toDoubleOrNull() ?: 0.0
                if (amountToTransfer > 0 && amountToTransfer <= currentAmount) {
                    currentAmount -= amountToTransfer
                    updateAmount(amount)
                    Toast.makeText(this, "Dinero transferido: $amountToTransfer", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Saldo insuficiente o monto inválido", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancelar", null)
            .create()
        dialog.show()
    }

    private fun showWithdrawDialog(saldoTextView: TextView) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_withdraw, null)
        val dialog = android.app.AlertDialog.Builder(this)
            .setTitle("Retirar Dinero")
            .setView(dialogView)
            .setPositiveButton("Retirar") { _, _ ->
                val amountToWithdraw = dialogView.findViewById<EditText>(R.id.amount).text.toString().toDoubleOrNull() ?: 0.0
                if (amountToWithdraw > 0 && amountToWithdraw <= currentAmount) {
                    currentAmount -= amountToWithdraw
                    updateAmount(saldoTextView)
                    Toast.makeText(this, "Dinero retirado: $amountToWithdraw", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Saldo insuficiente o monto inválido", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancelar", null)
            .create()
        dialog.show()
    }

    private fun showDepositDialog(saldoTextView: TextView) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_deposit, null)
        val dialog = android.app.AlertDialog.Builder(this)
            .setTitle("Depositar Dinero")
            .setView(dialogView)
            .setPositiveButton("Depositar") { _, _ ->
                val amountToDeposit = dialogView.findViewById<EditText>(R.id.amount).text.toString().toDoubleOrNull() ?: 0.0
                if (amountToDeposit > 0) {
                    currentAmount += amountToDeposit
                    updateAmount(saldoTextView)
                    Toast.makeText(this, "Dinero depositado: $amountToDeposit", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Monto inválido", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancelar", null)
            .create()
        dialog.show()
    }

    private fun updateAmount(saldoTextView: TextView) {
        saldoTextView.text = String.format("$%,.2f", currentAmount)
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