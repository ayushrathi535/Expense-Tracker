package com.example.expensetracker.presentation.HomeScreen.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.expensetracker.data.ExpenseDatabase

open class HomeViewModelFactory(private val context:Context): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)){
            val dao=ExpenseDatabase.getDatabase(context).expenseDao()
            return HomeViewModel(dao) as T
        }
        else{
            throw IllegalArgumentException("Unknown View Model Class")
        }
    }

}