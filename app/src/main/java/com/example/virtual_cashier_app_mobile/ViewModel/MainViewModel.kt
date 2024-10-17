package com.example.virtual_cashier_app_mobile.ViewModel

import androidx.lifecycle.ViewModel
import com.example.virtual_cashier_app_mobile.Repository.MainRepository

class MainViewModel(val repository:MainRepository):ViewModel() {
    constructor():this(MainRepository())

    fun loadData() = repository.items
    fun loadBudget() = repository.budget
}
