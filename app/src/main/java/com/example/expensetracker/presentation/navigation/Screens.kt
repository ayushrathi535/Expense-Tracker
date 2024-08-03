package com.example.expensetracker.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.expensetracker.R

sealed class Screen(
    val route: String?, val title: String? = null, val selectedIcon: Int? = null,
    val unselectedIcon: Int? = null
) {
    object Home : Screen("home", "Home", R.drawable.ic_home_filled, R.drawable.ic_home_outlined)
    object Profile : Screen("profile", "Profile", R.drawable.ic_profile_filled, R.drawable.ic_profile_outlined)

    object Wallet : Screen("wallet", "Wallet", R.drawable.ic_wallet_filled, R.drawable.ic_wallet_outlined)

    object Stats : Screen("stats", "Stats", R.drawable.ic_stat_filled, R.drawable.ic_stat_outlined)
    object AddExpense : Screen("addExpense", "Add Expense", R.drawable.ic_add, R.drawable.ic_add)

}

