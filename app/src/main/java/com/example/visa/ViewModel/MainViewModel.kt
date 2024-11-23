package com.example.visa.ViewModel

import androidx.lifecycle.ViewModel
import com.example.visa.Repository.MainRepository

class MainViewModel(val repository:MainRepository):ViewModel() {
    constructor():this(MainRepository())

    fun loadData() = repository.items
    fun loadBudget() = repository.budget
}
