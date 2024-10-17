package com.example.virtual_cashier_app_mobile.Repository

import com.example.virtual_cashier_app_mobile.Domain.BudgetDomain
import com.example.virtual_cashier_app_mobile.Domain.ExpenseDomain

class MainRepository {
    val items = mutableListOf(
        ExpenseDomain("Restaurant", 573.12, "img1", "6 dic 2024 19:15"),
        ExpenseDomain("McDonald", 77.82, "img2", "5 dic 2024 13:57"),
        ExpenseDomain("Cinema", 23.47, "img3", "5 dic 2024 20:45"),
        ExpenseDomain("Restaurant", 341.12, "img1", "4 dic 2024 22:18"),
    )

    val budget= mutableListOf(
        BudgetDomain("Préstamo hipotecario", 1200.0, 80.8),
        BudgetDomain("Suscripción", 1200.0, 10.0),
        BudgetDomain("Préstamo de auto", 800.0, 30.0)
    )
}