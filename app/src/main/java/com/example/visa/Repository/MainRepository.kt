package com.example.visa.Repository

import com.example.visa.Domain.BudgetDomain
import com.example.visa.Domain.ExpenseDomain

class MainRepository {
    val items = mutableListOf(
        ExpenseDomain("Restaurante", 573.12, "img1", "6 dic 2024 19:15"),
        ExpenseDomain("McDonald's", 77.82, "img2", "5 dic 2024 13:57"),
        ExpenseDomain("Cine", 23.47, "img3", "5 dic 2024 20:45"),
    )

    val budget= mutableListOf(
        BudgetDomain("Préstamo hipotecario", 1200.0, 80.8),
        BudgetDomain("Suscripción", 1200.0, 10.0),
        BudgetDomain("Préstamo de auto", 800.0, 30.0)
    )
}